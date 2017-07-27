package com.daykm.p5executioner.info

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModelWithView
import com.daykm.p5executioner.R
import com.daykm.p5executioner.util.txtAppr
import com.daykm.p5executioner.view.Pageable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textView
import javax.inject.Inject


class InfoAdapter @Inject constructor() : EpoxyController(), Pageable {
    override fun manager(ctx: Context): RecyclerView.LayoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                    .apply { recycleChildrenOnDetach = true }

    override fun attach() = requestModelBuild()
    override fun adapter(): RecyclerView.Adapter<*> = adapter
    override fun buildModels() = SourceModel().id(1).addTo(this)
}

class SourceModel : EpoxyModelWithView<TextView>() {
    override fun buildView(parent: ViewGroup): TextView = with(AnkoContext.create(parent.context)) {
        textView {
            text = "github.com/Daykm/P5Executioner"
            gravity = Gravity.CENTER
            Linkify.addLinks(this, Linkify.WEB_URLS)
            txtAppr(R.style.Base_TextAppearance_AppCompat_Title)
        }
    }
}

