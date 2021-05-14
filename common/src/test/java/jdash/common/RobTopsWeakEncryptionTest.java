package jdash.common;

import org.junit.jupiter.api.Test;

import static jdash.common.RobTopsWeakEncryption.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class RobTopsWeakEncryptionTest {

    @Test
    public void decodeAccountPasswordTest() {
        assertEquals("pl62kP1j1vgB0x3g", decodeAccountPassword("Q1sDAF1jBl8DQFR1BUoFVA=="));
    }

    @Test
    public void encodeAccountPasswordTest() {
        assertEquals("Q1sDAF1jBl8DQFR1BUoFVA==", encodeAccountPassword("pl62kP1j1vgB0x3g"));
    }

    @Test
    public void decodeLevelPassTest() {
        assertEquals("1098933", decodeLevelPass("AwYKDg0BBQ=="));
    }

    @Test
    public void encodeLevelPassTest() {
        assertEquals("AwYKDg0BBQ==", encodeLevelPass("1098933"));
    }

    @Test
    public void decodePrivateMessageBodyTest() {
        assertEquals("hello :)", decodePrivateMessageBody("WVFeWV4RDhs="));
    }

    @Test
    public void encodePrivateMessageBodyTest() {
        assertEquals("WVFeWV4RDhs=", encodePrivateMessageBody("hello :)"));
    }

    @Test
    public void encodeChkTest() {
        assertEquals("BFxRDAIFDlZeV1cKAAFVBwoADANRXQoKAAZbAAkAVA9UCwBTCgpeCQ==",
                encodeChk(69101896, 2, "0ej5u5v6Nl", 98006, "00000000-24d6-c259-ffff-ffffddb46189", 4063664,
                        "ysg6pUrtjn0J"));
    }
}