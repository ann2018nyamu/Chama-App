package com.ekenya.echama.model

import okhttp3.RequestBody
import okio.*
import java.io.IOException


class CountingRequestBody(
    private val requestBody: RequestBody,
    private val onProgressUpdate: CountingRequestListener
) : RequestBody() {
    override fun contentType() = requestBody.contentType()

    @Throws(IOException::class)
    override fun contentLength() = requestBody.contentLength()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink, this, onProgressUpdate)
        val bufferedSink = countingSink.buffer()

        requestBody.writeTo(bufferedSink)

        bufferedSink.flush()
    }
}
typealias CountingRequestListener = (bytesWritten: Long, contentLength: Long) -> Unit

class CountingSink(
    sink: Sink,
    private val requestBody: RequestBody,
    private val onProgressUpdate: CountingRequestListener
) : ForwardingSink(sink) {
    private var bytesWritten = 0L

    override fun write(source: Buffer, byteCount: Long) {
        super.write(source, byteCount)

        bytesWritten += byteCount
        onProgressUpdate(bytesWritten, requestBody.contentLength())
    }
}
sealed class CountingRequestResult<ResultT> {
    data class Progress<ResultT>(
        val progressFraction: Double
    ) : CountingRequestResult<ResultT>()

    data class Completed<ResultT>(
        val result: ResultT
    ) : CountingRequestResult<ResultT>()
}