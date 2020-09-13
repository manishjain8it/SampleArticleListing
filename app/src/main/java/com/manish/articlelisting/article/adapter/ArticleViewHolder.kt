package com.manish.articlelisting.article.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.manish.articlelisting.R
import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.util.convertTimeToText
import com.manish.articlelisting.util.withSuffix
import kotlinx.android.synthetic.main.listitem_article.view.*

class ArticleViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val requestOptions = RequestOptions().apply {
        placeholder(R.drawable.ic_launcher_background)
        error(R.drawable.ic_launcher_background)
    }

    fun bind(article: ArticleItem) {
        itemView.tvArticleDesc.text = article.content
        itemView.tvDuration.text = convertTimeToText(article.createdAt)
        itemView.tvLikesCount.text =
            String.format(itemView.context.getString(R.string.show_like), withSuffix(article.likes).toString())

        itemView.tvCommentCount.text =
            String.format(itemView.context.getString(R.string.show_comments), withSuffix(article.comments).toString())

        if (article.user.isNotEmpty()) {
            itemView.tvUserName.text =
                String.format(itemView.context.getString(R.string.show_name), article.user[0].name, article.user[0].lastname)
            itemView.tvUserDesignation.text = article.user[0].designation

            val userImageUrl = article.user[0].avatar
            loadImageUsingGlide(userImageUrl, DownsampleStrategy.CENTER_INSIDE, itemView.ivUserProfile)
        }
        if (article.media.isNotEmpty()) {
            itemView.tvArticleTitle.text = article.media[0].title
            itemView.tvArticleLink.text = article.media[0].url
            val articleImageUrl = article.media[0].image
            loadImageUsingGlide(articleImageUrl, DownsampleStrategy.DEFAULT, itemView.ivArticleImage)

            itemView.ivArticleImage.visibility = View.VISIBLE
            itemView.tvArticleTitle.visibility = View.VISIBLE
            itemView.tvArticleLink.visibility = View.VISIBLE
        } else {
            itemView.ivArticleImage.visibility = View.GONE
            itemView.tvArticleTitle.visibility = View.GONE
            itemView.tvArticleLink.visibility = View.GONE
        }
    }

    private fun loadImageUsingGlide(imageUrl: String, downSampleStrategy: DownsampleStrategy, view: ImageView) {
        Glide.with(itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .downsample(downSampleStrategy)
            .into(view)
    }
}