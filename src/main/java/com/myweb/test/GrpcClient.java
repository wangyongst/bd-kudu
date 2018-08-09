package com.myweb.test;

import com.anoyi.grpc.service.GrpcRequest;
import com.anoyi.grpc.util.ProtobufUtils;
import com.anoyi.rpc.CommonServiceGrpc;
import com.anoyi.rpc.GrpcService;
import com.google.protobuf.ByteString;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
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
        GrpcClient client = new GrpcClient("127.0.0.1", 9528);
        try {
            client.say();
        } finally {
            client.shutdown();
        }
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private void say() throws NoSuchMethodException, CharacterCodingException, UnsupportedEncodingException {
        GrpcRequest grpcRequest = new GrpcRequest();
        grpcRequest.setClazz(OneService.class);
        grpcRequest.setMethod(OneService.class.getMethod("queryDepthPriceRaw", Parameter.class));
        Parameter parameter = new Parameter();
        parameter.setStartTimestamp(1533788447216L);
        parameter.setEndTimestamp(1533788447300L);
        Object[] paramters = {parameter};
        grpcRequest.setArgs(paramters);
        byte[] bytes = ProtobufUtils.serialize(grpcRequest);
        GrpcService.Request request = GrpcService.Request.newBuilder().setRequest(ByteString.copyFrom(bytes)).build();
        GrpcService.Response response = blockingStub.handle(request);
        System.out.println(response.getReponse());
    }
}
