/*******************************************************************************
 * Copyright 2022-2022 Exactpro (Exactpro Systems Limited)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.exactpro.th2.common.grpc.ConnectionID
import com.exactpro.th2.common.grpc.Direction
import com.exactpro.th2.common.grpc.MessageID
import com.exactpro.th2.dataprovider.grpc.*
import com.google.protobuf.Empty
import com.google.protobuf.Int32Value
import com.google.protobuf.Timestamp
import io.grpc.ClientStreamTracer
import java.time.Instant

fun getMessagesStreams(grpc: DataProviderService) {
    grpc.getMessageStreams(Empty.getDefaultInstance()).also {
        println("Message Streams" + "-".repeat(10))
        println(it)
    }
}

fun getMessage(grpc: DataProviderService) {
    grpc.getMessage(
        MessageID.newBuilder()
            .setSequence(1643813539896395647)
            .setDirection(Direction.FIRST)
            .setConnectionId(
                ConnectionID.newBuilder()
                    .setSessionAlias("arfq01fix04")
                    .build()
            )
            .build()
    ).also {
        println("Message " + "-".repeat(10))
        println(it)
    }
}

fun searchMessages(grpc: DataProviderService) {
    val timeFrom = Instant.parse("2022-03-04T10:00:00Z")
    val timeTo = Instant.parse("2022-03-04T11:00:00Z")
    val stream = grpc.searchMessages(
        MessageSearchRequest.newBuilder()
            .setSearchDirection(TimeRelation.NEXT)
            .setResultCountLimit(Int32Value.of(10))
            .setStartTimestamp(Timestamp.newBuilder().setSeconds(timeFrom.epochSecond).setNanos(timeFrom.nano))
            .setStartTimestamp(Timestamp.newBuilder().setSeconds(timeTo.epochSecond).setNanos(timeTo.nano))
            .setStream(StringList.newBuilder().addAllListString(listOf("amqp_07_04")).build())
            .addAllMessageId(listOf(MessageID.newBuilder()
                .setConnectionId(ConnectionID.newBuilder().setSessionAlias("amqp_07_04"))
                .setDirection(Direction.SECOND)
                .setSequence(-1)
                .build(),
                MessageID.newBuilder()
                    .setConnectionId(ConnectionID.newBuilder().setSessionAlias("amqp_07_04"))
                    .setDirection(Direction.FIRST)
                    .setSequence(320168121)
                    .build())
            )
            .build()
    )
    println("Messages " + "-".repeat(10))
    stream.forEachRemaining {
        println(it)
    }
    println("End messages " + "-".repeat(10))
}