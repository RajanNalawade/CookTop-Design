package com.example.designcooktop

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import com.example.designcooktop.model.CookTopsItem
import com.google.android.material.imageview.ShapeableImageView

@SuppressLint("ViewConstructor")
class CookTopConstraintLayout(context: Context, private val viewsData: CookTopsItem) :
    ConstraintLayout(context) {

    private val listZones = ArrayList<ShapeableImageView>()
    private val constraintSet = ConstraintSet()
    private lateinit var centerZone: ShapeableImageView
    private lateinit var ivWhirlpoolLogo: ShapeableImageView
    private lateinit var ivFlexiZone1: ShapeableImageView

    init {
        id = generateViewId()
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        background = ResourcesCompat.getDrawable(resources, R.color.black, context.theme)

        constraintSet.clone(this)

        val noOfViews = viewsData.details.rows.toInt() * viewsData.details.columns.toInt()
        for (view in 0 until noOfViews) {
            listZones.add(
                ShapeableImageView(context)
            )
        }

        addWhirlpoolLogoConstraints()

        addLargeZoneConstraints()

        viewsData.details.rowsColumns.forEachIndexed { index, rowsColumn ->

            when (index) {
                0 -> {
                    addRegularZones(index, rowsColumn.isCircle.toBoolean(), rowsColumn.circleType)
                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.START,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.START,
                        32
                    )
                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        32
                    )
                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.BOTTOM,
                        listZones[3].id,
                        ConstraintSet.TOP
                    )
                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.END,
                        centerZone.id,
                        ConstraintSet.START,
                        32
                    )

                }

                1 -> {
                    if (viewsData.details.positionLargeZOne != "center") {
                        addRegularZones(
                            index, rowsColumn.isCircle.toBoolean(), rowsColumn.circleType
                        )
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.START,
                            listZones[0].id,
                            ConstraintSet.END,
                            32
                        )
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.TOP,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.TOP,
                            32
                        )
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.END,
                            listZones[index + 1].id,
                            ConstraintSet.START,
                            32
                        )
                    }
                }

                2 -> {
                    addRegularZones(index, rowsColumn.isCircle.toBoolean(), rowsColumn.circleType)
                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        32
                    )
                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.END,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.END,
                        32
                    )

                    if (noOfViews == 6) {
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.BOTTOM,
                            listZones[5].id,
                            ConstraintSet.TOP
                        )
                    }
                }

                3 -> {
                    addRegularZones(index, rowsColumn.isCircle.toBoolean(), rowsColumn.circleType)
                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.START,
                        listZones[0].id,
                        ConstraintSet.START
                    )

                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.TOP,
                        listZones[0].id,
                        ConstraintSet.BOTTOM,
                        32
                    )

                    constraintSet.connect(
                        listZones[index].id, ConstraintSet.END, listZones[0].id, ConstraintSet.END
                    )

                    constraintSet.connect(
                        listZones[index].id,
                        ConstraintSet.BOTTOM,
                        ivWhirlpoolLogo.id,
                        ConstraintSet.TOP
                    )
                }

                4 -> {
                    if (viewsData.details.positionLargeZOne != "center") {
                        addRegularZones(
                            index, rowsColumn.isCircle.toBoolean(), rowsColumn.circleType
                        )
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.START,
                            listZones[index - 1].id,
                            ConstraintSet.END,
                            32
                        )
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.TOP,
                            listZones[1].id,
                            ConstraintSet.BOTTOM,
                            32
                        )
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.END,
                            listZones[index + 1].id,
                            ConstraintSet.START,
                            32
                        )
                    }
                }

                5 -> {
                    if (rowsColumn.isZoneAvailable.toBoolean()) {
                        addRegularZones(
                            index, rowsColumn.isCircle.toBoolean(), rowsColumn.circleType
                        )
                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.END,
                            listZones[2].id,
                            ConstraintSet.END,
                            32
                        )

                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.START,
                            listZones[2].id,
                            ConstraintSet.START
                        )

                        constraintSet.connect(
                            listZones[index].id,
                            ConstraintSet.BOTTOM,
                            ivWhirlpoolLogo.id,
                            ConstraintSet.TOP
                        )
                    }
                }
            }
        }

        constraintSet.applyTo(this)
    }

    private fun addLargeZoneConstraints() {

        centerZone = ShapeableImageView(context)
        centerZone.id = generateViewId()
        centerZone.setImageDrawable(
            ResourcesCompat.getDrawable(resources, R.drawable.circle_240, context.theme)
        )
        addView(centerZone)

        constraintSet.constrainWidth(centerZone.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(centerZone.id, ConstraintSet.WRAP_CONTENT)

        when (viewsData.details.positionLargeZOne) {
            "center" -> {
                constraintSet.connect(
                    centerZone.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP
                )

                if (viewsData.name == "NAR-08") {
                    constraintSet.connect(
                        centerZone.id, ConstraintSet.BOTTOM, ivWhirlpoolLogo.id, ConstraintSet.TOP
                    )
                } else {
                    constraintSet.connect(
                        centerZone.id,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM
                    )
                }

                constraintSet.connect(
                    centerZone.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START
                )
                constraintSet.connect(
                    centerZone.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END
                )
            }
        }
    }

    private fun addRegularZones(index: Int, isCircle: Boolean, circleType: String) {
        listZones[index].id = generateViewId()
        listZones[index].setImageDrawable(
            ResourcesCompat.getDrawable(
                resources, getCircleRectFromType(
                    isCircle, index, circleType
                ), context.theme
            )
        )
        addView(listZones[index])

        constraintSet.constrainWidth(listZones[index].id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(listZones[index].id, ConstraintSet.WRAP_CONTENT)
    }

    private fun getCircleRectFromType(
        isCircle: Boolean, pos: Int, type: String, isFlexi: Boolean = false
    ): Int {
        var resourceID = 0

        if (isFlexi && isCircle) {
            resourceID = R.drawable.flexi_circular
        } else if (isFlexi && !isCircle) {
            resourceID = R.drawable.flexi_rectangle
        } else if (isCircle) {
            resourceID = when (type) {
                "145" -> R.drawable.circle_145
                "180" -> R.drawable.circle_180
                "210" -> R.drawable.circle_210
                "240" -> R.drawable.circle_240
                else -> {
                    0
                }
            }
        } else {
            resourceID = when (pos) {
                0, 2 -> R.drawable.half_rectangle_up
                3, 5 -> R.drawable.half_rectangle_down
                else -> {
                    0
                }
            }
        }
        return resourceID
    }

    private fun addWhirlpoolLogoConstraints() {

        //for whirlpool logo
        ivWhirlpoolLogo = ShapeableImageView(context)
        ivWhirlpoolLogo.id = View.generateViewId()
        ivWhirlpoolLogo.setPadding(16)
        ivWhirlpoolLogo.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources, R.drawable.cooktop_whirlpool_logo, context.theme
            )
        )
        addView(ivWhirlpoolLogo)

        constraintSet.constrainWidth(ivWhirlpoolLogo.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(ivWhirlpoolLogo.id, ConstraintSet.WRAP_CONTENT)

        constraintSet.connect(
            ivWhirlpoolLogo.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            32
        )
        constraintSet.connect(
            ivWhirlpoolLogo.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START
        )
        constraintSet.connect(
            ivWhirlpoolLogo.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END
        )
    }

    private fun addFlexiZoneFirstColumnConstraints() {

        ivFlexiZone1 = ShapeableImageView(context)
        ivFlexiZone1.id = View.generateViewId()
        ivFlexiZone1.setPadding(24)
        ivFlexiZone1.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources, getCircleRectFromType(
                    viewsData.details.rowsColumns[0].isCircle.toBoolean(),
                    0,
                    viewsData.details.rowsColumns[0].circleType,
                    viewsData.details.rowsColumns[0].isFlexi.toBoolean()
                ), context.theme
            )
        )

        constraintSet.constrainWidth(ivFlexiZone1.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(ivFlexiZone1.id, ConstraintSet.MATCH_CONSTRAINT)

        addView(ivFlexiZone1)

        constraintSet.connect(
            ivFlexiZone1.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 32
        )
        constraintSet.connect(
            ivFlexiZone1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 32
        )

        constraintSet.connect(
            ivFlexiZone1.id, ConstraintSet.BOTTOM, ivWhirlpoolLogo.id, ConstraintSet.TOP
        )

        constraintSet.connect(
            ivFlexiZone1.id, ConstraintSet.END, centerZone.id, ConstraintSet.START
        )
    }

    fun handleFirstFlexiZone(isOn: Boolean) {
        if (isOn) {
            removeView(listZones[0])
            removeView(listZones[3])

            addFlexiZoneFirstColumnConstraints()
        } else {
            addView(listZones[0])
            addView(listZones[3])

            removeView(ivFlexiZone1)
        }

        ivWhirlpoolLogo.updateLayoutParams<LayoutParams> {
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            if (listZones[3].isAttachedToWindow) {/*constraintSet.connect(
                    ivWhirlpoolLogo.id, ConstraintSet.TOP, listZones[3].id, ConstraintSet.BOTTOM
                )*/
                this.topToBottom = listZones[3].id
            } else if (ivFlexiZone1.isAttachedToWindow) {

                /*constraintSet.connect(
                    ivWhirlpoolLogo.id, ConstraintSet.TOP, ivFlexiZone1.id, ConstraintSet.BOTTOM
                )*/
                this.topToBottom = ivFlexiZone1.id
            } /*else if (r2c3.isAttachedToWindow) {
                constraintSet.connect(
                    ivWhirlpoolLogo.id, ConstraintSet.TOP, r2c3.id, ConstraintSet.BOTTOM, 32
                )
                this.topToBottom = r2c3.id
            } else if(flexR1C3.isAttachedToWindow) {
                constraintSet.connect(
                    ivWhirlpoolLogo.id, ConstraintSet.TOP, flexR1C3.id, ConstraintSet.BOTTOM, 32
                )
                this.topToBottom = flexR1C3.id
            }*/ else {/*constraintSet.connect(
                    ivWhirlpoolLogo.id, ConstraintSet.TOP, centerZone.id, ConstraintSet.BOTTOM
                )*/
                this.topToBottom = centerZone.id
            }

            /*if (viewsData.details.rowsColumns[5].isZoneAvailable.toBoolean()){
                listZones[5].updateLayoutParams<LayoutParams> {

                    constraintSet.connect(
                        listZones[5].id,
                        ConstraintSet.END,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.END,
                        32
                    )

                    if (listZones[3].isAttachedToWindow) {
                        constraintSet.connect(
                            listZones[5].id, ConstraintSet.BOTTOM, listZones[3].id, ConstraintSet.BOTTOM
                        )
                    } else {
                        constraintSet.connect(
                            listZones[5].id, ConstraintSet.BOTTOM, ivWhirlpoolLogo.id, ConstraintSet.TOP
                        )
                    }
                }
            }*/
        }
    }

}