package com.daykm.p5executioner

import android.content.Context
import com.daykm.p5executioner.proto.Data
import io.reactivex.Single
import javax.inject.Inject

class DataRepo @Inject constructor(ctx: Context) {

  val DATA =
      Single.fromCallable {
        Data.parseFrom(ctx.assets.open("data.pb"))
      }.cache()

}