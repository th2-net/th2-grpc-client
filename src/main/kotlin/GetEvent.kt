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

import com.exactpro.th2.common.grpc.EventID
import com.exactpro.th2.dataprovider.grpc.DataProviderService
import com.exactpro.th2.dataprovider.grpc.EventSearchRequest
import com.exactpro.th2.dataprovider.grpc.TimeRelation
import com.google.protobuf.BoolValue
import com.google.protobuf.Int32Value
import com.google.protobuf.Timestamp
import java.time.Instant

fun getEvent(grpc: DataProviderService) {
    grpc.getEvent(
        EventID.newBuilder()
            .setId("61d6247b-92e6-11ec-8f1f-0b22159f692b")
            .build()
    ).also {
        println("Event" + "-".repeat(10))
        println(it)
    }
}

fun searchEventsMetadata(grpc: DataProviderService) {
    val start = Instant.parse("2022-02-21T07:15:24.240Z")
    grpc.searchEvents(
        EventSearchRequest.newBuilder()
            .setResultCountLimit(Int32Value.of(10))
            .setStartTimestamp(Timestamp.newBuilder().setSeconds(start.epochSecond).setNanos(start.nano))
            .setSearchDirection(TimeRelation.NEXT)
            .build()
    ).also {
        println("search events" + "-".repeat(10))
        it.forEachRemaining {
            println(it)
        }
    }
}


fun searchEvents(grpc: DataProviderService) {
    val start = Instant.parse("2022-01-01T07:15:24.240Z")
    grpc.searchEvents(
        EventSearchRequest.newBuilder()
            .setResultCountLimit(Int32Value.of(10))
            .setStartTimestamp(Timestamp.newBuilder().setSeconds(start.epochSecond).setNanos(start.nano))
            .setSearchDirection(TimeRelation.NEXT)
            .setMetadataOnly(BoolValue.of(false))
            .build()
    ).also {
        println("search events" + "-".repeat(10))
        it.forEachRemaining {
            println(it)
        }
    }
}
