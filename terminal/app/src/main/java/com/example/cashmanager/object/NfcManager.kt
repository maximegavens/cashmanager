package com.example.cashmanager.`object`

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.os.Parcelable
import android.util.Log
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.charset.Charset
import kotlin.experimental.and

object NfcManager {
    fun resolveIntentAndGetTagView(intent: Intent): String {
        val action = intent.action

        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val messageArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            val messages: Array<NdefMessage?>

            if (messageArray != null) {
                messages = arrayOfNulls(messageArray.size)

                for (i in messageArray.indices) {
                    messages[i] = messageArray[i] as NdefMessage
                }

            } else {
                val empty = ByteArray(0)
                val id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
                val tag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag
                val payload = dumpTagData(tag).toByteArray()
                val record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload)
                val msg = NdefMessage(arrayOf(record))
                messages = arrayOf(msg)
            }

            return buildTagViews(messages)
        }

        return ""
    }

    fun buildTagViews(msgs: Array<NdefMessage?>): String {
        if (msgs.isEmpty()) return ""

        var text = ""
        val payload = msgs[0]!!.records[0].payload
        val isUtf8 = payload[0] and 128.toByte()
        val textEncoding: Charset
        if (isUtf8.toInt() == 0) {
            textEncoding = Charsets.UTF_8
        } else {
            textEncoding = Charsets.UTF_16
        }
        val languageCodeLength = payload[0] and 51

        try {
            text = String(
                payload,
                languageCodeLength + 1,
                payload.size - languageCodeLength - 1,
                textEncoding
            )
        } catch (e: UnsupportedEncodingException) {
            Log.e("UnsupportedEncoding", e.toString())
        }

        val t = "NFC Content\n".plus(text)
        return t
    }

    fun dumpTagData(tag: Tag): String {
        val sb = StringBuilder()
        val id = tag.id
        sb.append("ID : ").append(id.toString()).append('\n')
        sb.append("ID (hex): ").append(id.toHexString()).append('\n')
        sb.append("ID (dec): ").append(ByteToDec(id).toString()).append('\n')

        val prefix = "android.nfc.tech."
        sb.append("Technologies: ")
        for (tech in tag.techList) {
            sb.append(tech.substring(prefix.length))
            sb.append(", ")
        }

        sb.delete(sb.length - 2, sb.length)

        for (tech in tag.techList) {
            if (tech == MifareClassic::class.java.name) {
                sb.append('\n')
                var type = "Unknown"

                try {
                    val mifareTag = MifareClassic.get(tag)

                    when (mifareTag.type) {
                        MifareClassic.TYPE_CLASSIC -> type = "Classic"
                        MifareClassic.TYPE_PLUS -> type = "Plus"
                        MifareClassic.TYPE_PRO -> type = "Pro"
                    }
                    sb.append("Mifare Classic type: ")
                    sb.append(type)
                    sb.append('\n')

                    sb.append("Mifare size: ")
                    sb.append(mifareTag.size.toString() + " bytes")
                    sb.append('\n')

                    sb.append("Mifare sectors: ")
                    sb.append(mifareTag.sectorCount)
                    sb.append('\n')

                    sb.append("Mifare blocks: ")
                    sb.append(mifareTag.blockCount)
                } catch (e: Exception) {
                    sb.append("Mifare classic error: " + e.message)
                }

            }

            if (tech == MifareUltralight::class.java.name) {
                sb.append('\n')
                val mifareUlTag = MifareUltralight.get(tag)
                var type = "Unknown"
                when (mifareUlTag.type) {
                    MifareUltralight.TYPE_ULTRALIGHT -> type = "Ultralight"
                    MifareUltralight.TYPE_ULTRALIGHT_C -> type = "Ultralight C"
                }
                sb.append("Mifare Ultralight type: ")
                sb.append(type)
            }
        }

        return sb.toString()
    }

    private fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

    private fun ByteToDec(byte: ByteArray): Float {
        return ByteBuffer.wrap(byte).float
    }
}