package org.openweathermap.api.gson

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class SecondsDateTypeAdapterSpockTest extends Specification {
    SecondsDateTypeAdapter adapter = new SecondsDateTypeAdapter()

    def "should write Date into JsonWriter as long"() {
        given:
        def jsonWriter = Mock(JsonWriter)
        def date = new Date(116, 5, 5)
        when:
        adapter.write(jsonWriter, date)
        then:
        1 * jsonWriter.value(_ as Long)

    }

    def "should write null into JsonWriter when date is null"() {
        given:
        def jsonWriter = Mock(JsonWriter)
        def date = null
        when:
        adapter.write(jsonWriter, date)
        then:
        1 * jsonWriter.nullValue();
    }

    def "should return null when reading null from JsonReader"() {
        given:
        def jsonReader = Mock(JsonReader)
        when:
        def actual = adapter.read(jsonReader)
        then:
        1 * jsonReader.peek() >> JsonToken.NULL
        1 * jsonReader.nextNull()
        actual == null
    }

    def "should return Date when reading seconds from JsonReader"() {
        given:
        def jsonReader = Mock(JsonReader)
        def timeInSeconds = 1465074000L
        when:
        def actual = adapter.read(jsonReader)
        then:
        1 * jsonReader.peek() >> JsonToken.NUMBER
        1 * jsonReader.nextLong() >> timeInSeconds
        actual.getTime() == TimeUnit.SECONDS.toMillis(timeInSeconds)
    }
}
