package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageOfDayTitle")
fun bindImageOfDayTitle(textView: TextView, title: String) {
    if (!title.isNullOrBlank()) {
        textView.text = title
    } else {
        textView.setText(R.string.image_of_the_day)
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("statusIconDescription")
fun bindAsteroidStatusImageDescription(imageView: ImageView, isHazardous: Boolean) {
    val res = imageView.context.resources
    imageView.contentDescription = if (isHazardous) {
        res.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        res.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("asteroidStatusImageDescription")
fun bindDetailsStatusImageDescription(imageView: ImageView, isHazardous: Boolean) {
    val res = imageView.context.resources
    imageView.contentDescription = if (isHazardous) {
        res.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        res.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
