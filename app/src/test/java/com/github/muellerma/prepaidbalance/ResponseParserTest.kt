package com.github.muellerma.prepaidbalance

import com.github.muellerma.prepaidbalance.utils.ResponseParser
import org.junit.Assert.assertEquals
import org.junit.Test

class ResponseParserTest {
    @Test
    fun testGetBalance() {
        assertEquals(null, ResponseParser.getBalance(null))
        assertEquals(null, ResponseParser.getBalance(""))
        assertEquals(null, ResponseParser.getBalance("foobar"))
        assertEquals(2.42, ResponseParser.getBalance("2.42"))
        assertEquals(2.42, ResponseParser.getBalance("2,42"))
        assertEquals(2.42, ResponseParser.getBalance("02,42"))
        assertEquals(1202.42, ResponseParser.getBalance("1202,42"))
        assertEquals(1202.0, ResponseParser.getBalance("1202"))
        assertEquals(1202.0, ResponseParser.getBalance("1202.0"))
        assertEquals(1202.0, ResponseParser.getBalance("1202.00"))

        assertEquals(5.12, ResponseParser.getBalance("5.12 EUR"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is 5.12 EUR"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance: 5.12 EUR."))
        assertEquals(5.12, ResponseParser.getBalance("Current balance: 5.12 EUR. foobar"))
        assertEquals(5.12, ResponseParser.getBalance("5.12 EUR is the current balance"))
        assertEquals(5.12, ResponseParser.getBalance("Some ads; 5.12 EUR is the current balance"))
        assertEquals(5.12, ResponseParser.getBalance("Some ads. 5.12 EUR is the current balance"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is 5,12 EUR"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is 5.12 EURO"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is 5.12 €"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is € 5.12"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is 5.12 USD"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is $ 5.12"))
        assertEquals(5.12, ResponseParser.getBalance("Current balance is 5.12"))

        // Vodafone Germany
        assertEquals(5.12, ResponseParser.getBalance("Aktuelles Guthaben: 5,12 EUR\n" +
                "Wähl bitte aus:\n" +
                "1 Aufladen\n" +
                "2 Guthaben & Verbrauch\n" +
                "3  Tarife & Optionen\n" +
                "4 Spracheinstellungen\n" +
                "5 Vorteilsangebot")
        )

        // https://github.com/mueller-ma/PrepaidBalance/issues/11#issuecomment-977514814
        assertEquals(1234.0, ResponseParser.getBalance("Pulsa Rp 1234 s.d. 01-01-2022 \n" +
                "1 8GB,Rp30rb/30hr\n" +
                "2 25GB,Rp50rb/30hr\n" +
                "22 Info")
        )
    }
}