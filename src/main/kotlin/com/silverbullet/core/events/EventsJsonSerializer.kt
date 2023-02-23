package com.silverbullet.core.events

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val eventsSerializationModule = SerializersModule {
    polymorphic(Event::class) {
        subclass(AddedToChannelEvent::class)
        subclass(ReceivedMessage::class)
    }
}

val eventsJsonSerializer = Json {
    classDiscriminator = "event"
    serializersModule = eventsSerializationModule
}