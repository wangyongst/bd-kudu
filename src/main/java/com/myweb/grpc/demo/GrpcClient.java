package com.myweb.grpc.demo;

import com.anoyi.grpc.service.GrpcRequest;
import com.anoyi.grpc.service.GrpcResponse;
import com.anoyi.grpc.util.ProtobufUtils;
import com.anoyi.rpc.CommonServiceGrpc;
import com.anoyi.rpc.GrpcService;
import com.google.protobuf.ByteString;
import com.myweb.domain.DepthPriceRaw;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
    private final ManagedChannel channel;
    private final CommonServiceGrpc.CommonServiceBlockingStub blockingStub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    private GrpcClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = CommonServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("13.250.35.50", 9528);
//        GrpcClient client = new GrpcClient("127.0.0.1", 9528);
        try {
            client.queryDepthPriceRaw();
        } finally {
            client.shutdown();
        }
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private void queryDepthPriceRaw() throws NoSuchMethodException, CharacterCodingException, UnsupportedEncodingException {
        GrpcRequest grpcRequest = new GrpcRequest();
        grpcRequest.setClazz(OneService.class);
        grpcRequest.setMethod(OneService.class.getMethod("queryDepthPriceRaw", Parameter.class));
        Parameter parameter = new Parameter();
        List<String> counterParty = new ArrayList<>();
        counterParty.add("huobi");
        parameter.setCounterParty(counterParty);
        List<String> symbol = new ArrayList<>();
        symbol.add("EOS-BTC");
        symbol.add("ETH-USDT");
        parameter.setSymbol(symbol);
        parameter.setStartTimestamp(new Date().getTime()-30000);
        parameter.setEndTimestamp(new Date().getTime());
        Object[] paramters = {parameter};
        grpcRequest.setArgs(paramters);
        byte[] bytes = ProtobufUtils.serialize(grpcRequest);
        GrpcService.Request request = GrpcService.Request.newBuilder().setRequest(ByteString.copyFrom(bytes)).build();
        GrpcService.Response response = blockingStub.handle(request);
        GrpcResponse out = ProtobufUtils.deserialize(response.getReponse().toByteArray(),GrpcResponse.class);
        List<DepthPriceRaw> depthPriceRaws = (ArrayList<DepthPriceRaw>)out.getResult();
        System.out.println(depthPriceRaws.size());
    }
}
