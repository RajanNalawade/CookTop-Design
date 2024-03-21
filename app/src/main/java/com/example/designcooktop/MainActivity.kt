package com.example.designcooktop

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import com.example.designcooktop.databinding.ActivityMainBinding
import com.example.designcooktop.model.CookTops
import com.example.designcooktop.model.CookTopsItem
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var listCookTops: List<CookTopsItem>
    private lateinit var cookTopLayout: CookTopConstraintLayout

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Observable.fromCallable {
            readJsonFileFromAssets(this)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { applianceList ->

                listCookTops = applianceList

                if (!applianceList.isNullOrEmpty()) {
                    val allAppliance = applianceList.map {
                        it.name
                    }

                    val adapter = ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_dropdown_item, allAppliance
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spAppliance.adapter = adapter
                }
            }

        binding.spAppliance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                binding.swFlexi.isChecked = false
                //createCookTopUI(listCookTops[position])

                cookTopLayout = CookTopConstraintLayout(this@MainActivity, listCookTops[position])
                binding.exampleTableLayoutCard.addView(cookTopLayout)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun readJsonFileFromAssets(
        context: Context, fileName: String = "cooktop.json"
    ): CookTops {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, CookTops::class.java)
    }

    private fun createCookTopUI(input: CookTopsItem) {

        binding.cvCookTopCard.removeAllViews()

        val mainConstraintLayout = ConstraintLayout(this)
        val constraintSet = ConstraintSet()
        constraintSet.clone(mainConstraintLayout)

        val constraintLayoutParam = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        )

        mainConstraintLayout.layoutParams = constraintLayoutParam
        mainConstraintLayout.background =
            ResourcesCompat.getDrawable(resources, R.color.black, theme)

        /*val iv = ShapeableImageView(this)
        iv.setOnClickListener {
            Toast.makeText(this, "Pressed", Toast.LENGTH_LONG).show()
        }
        val ivWhirlpoolLogo = ShapeableImageView(this)
        if (input.details.positionLargeZOne == "center") {
            iv.id = View.generateViewId()
            iv.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources, R.drawable.circle_240, theme
                )
            )
            iv.isClickable = true
            iv.isFocusable = true
            iv.isFocusableInTouchMode = true
            mainConstraintLayout.addView(iv)

            constraintSet.constrainWidth(iv.id, ConstraintSet.WRAP_CONTENT)
            constraintSet.constrainHeight(iv.id, ConstraintSet.WRAP_CONTENT)

            constraintSet.connect(
                iv.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 32
            )
            constraintSet.connect(
                iv.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 32
            )
            constraintSet.connect(
                iv.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 32
            )
            constraintSet.connect(
                iv.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 32
            )
        }

        val r1c1 = ShapeableImageView(this)
        val r1c2 = ShapeableImageView(this)
        val r1c3 = ShapeableImageView(this)
        val r2c1 = ShapeableImageView(this)
        val r2c2 = ShapeableImageView(this)
        val r2c3 = ShapeableImageView(this)
        val flexR1C1 = ShapeableImageView(this)
        val flexR1C3 = ShapeableImageView(this)*/

        /*input.details.rowsColumns.forEachIndexed { index, rowsColumn ->
            when (index) {
                0 -> {
                    r1c1.id = View.generateViewId()
                    r1c1.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources, getCircleRectFromType(
                                false, rowsColumn.isCircle.toBoolean(), index, rowsColumn.circleType
                            ), theme
                        )
                    )
                    mainConstraintLayout.addView(r1c1)

                    constraintSet.constrainWidth(r1c1.id, ConstraintSet.WRAP_CONTENT)
                    constraintSet.constrainHeight(r1c1.id, ConstraintSet.WRAP_CONTENT)

                    constraintSet.connect(
                        r1c1.id,
                        ConstraintSet.START,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.START,
                        32
                    )
                    constraintSet.connect(
                        r1c1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 32
                    )

                    constraintSet.connect(
                        r1c1.id, ConstraintSet.BOTTOM, r2c1.id, ConstraintSet.TOP
                    )
                }

                1 -> {
                    if (input.details.positionLargeZOne != "center") {
                        r1c2.id = View.generateViewId()
                        r1c2.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources, getCircleRectFromType(
                                    false,
                                    rowsColumn.isCircle.toBoolean(),
                                    index,
                                    rowsColumn.circleType
                                ), theme
                            )
                        )
                        mainConstraintLayout.addView(r1c2)

                        constraintSet.constrainWidth(r1c2.id, ConstraintSet.WRAP_CONTENT)
                        constraintSet.constrainHeight(r1c2.id, ConstraintSet.WRAP_CONTENT)

                        constraintSet.connect(
                            r1c2.id,
                            ConstraintSet.START,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.START,
                            32
                        )
                        constraintSet.connect(
                            r1c2.id,
                            ConstraintSet.TOP,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.TOP,
                            32
                        )
                        constraintSet.connect(
                            r1c2.id,
                            ConstraintSet.END,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.END,
                            32
                        )
                    }
                }

                2 -> {
                    r1c3.id = View.generateViewId()
                    r1c3.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources, getCircleRectFromType(
                                false, rowsColumn.isCircle.toBoolean(), index, rowsColumn.circleType
                            ), theme
                        )
                    )
                    mainConstraintLayout.addView(r1c3)

                    constraintSet.constrainWidth(r1c3.id, ConstraintSet.WRAP_CONTENT)
                    constraintSet.constrainHeight(r1c3.id, ConstraintSet.WRAP_CONTENT)

                    constraintSet.connect(
                        r1c3.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 32
                    )
                    constraintSet.connect(
                        r1c3.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 32
                    )
                    constraintSet.connect(
                        r1c3.id, ConstraintSet.BOTTOM, r2c3.id, ConstraintSet.TOP
                    )
                }

                3 -> {
                    r2c1.id = View.generateViewId()
                    r2c1.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources, getCircleRectFromType(
                                false, rowsColumn.isCircle.toBoolean(), index, rowsColumn.circleType
                            ), theme
                        )
                    )
                    mainConstraintLayout.addView(r2c1)

                    constraintSet.constrainWidth(r2c1.id, ConstraintSet.WRAP_CONTENT)
                    constraintSet.constrainHeight(r2c1.id, ConstraintSet.WRAP_CONTENT)

                    constraintSet.connect(
                        r2c1.id,
                        ConstraintSet.START,
                        r1c1.id,
                        ConstraintSet.START
                    )

                    constraintSet.connect(
                        r2c1.id, ConstraintSet.TOP, r1c1.id, ConstraintSet.BOTTOM, 32
                    )

                    constraintSet.connect(r2c1.id, ConstraintSet.END, r1c1.id, ConstraintSet.END)
                }

                4 -> {
                    if (input.details.positionLargeZOne != "center") {
                        r2c2.id = View.generateViewId()
                        r2c2.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources, getCircleRectFromType(
                                    false,
                                    rowsColumn.isCircle.toBoolean(),
                                    index,
                                    rowsColumn.circleType
                                ), theme
                            )
                        )
                        mainConstraintLayout.addView(r2c2)

                        constraintSet.constrainWidth(r2c2.id, ConstraintSet.WRAP_CONTENT)
                        constraintSet.constrainHeight(r2c2.id, ConstraintSet.WRAP_CONTENT)

                        constraintSet.connect(
                            r2c2.id,
                            ConstraintSet.START,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.START,
                            32
                        )
                        constraintSet.connect(
                            r2c2.id,
                            ConstraintSet.TOP,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.TOP,
                            32
                        )
                        constraintSet.connect(
                            r2c2.id,
                            ConstraintSet.END,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.END,
                            32
                        )
                    }
                }

                5 -> {
                    if (rowsColumn.isZoneAvailable.toBoolean()) {

                        r2c3.id = View.generateViewId()
                        r2c3.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources, getCircleRectFromType(
                                    false,
                                    rowsColumn.isCircle.toBoolean(),
                                    index,
                                    rowsColumn.circleType
                                ), theme
                            )
                        )
                        mainConstraintLayout.addView(r2c3)

                        constraintSet.constrainWidth(r2c3.id, ConstraintSet.WRAP_CONTENT)
                        constraintSet.constrainHeight(r2c3.id, ConstraintSet.WRAP_CONTENT)

                        constraintSet.connect(
                            r2c3.id,
                            ConstraintSet.END,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.END,
                            32
                        )

                        constraintSet.connect(
                            r2c3.id, ConstraintSet.TOP, r1c3.id, ConstraintSet.BOTTOM
                        )

                        constraintSet.connect(
                            r2c3.id,
                            ConstraintSet.BOTTOM,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.BOTTOM,
                            32
                        )
                    }
                }
            }
        }*/

        binding.swFlexi.setOnCheckedChangeListener { buttonView, isChecked ->

            cookTopLayout.handleFirstFlexiZone(isChecked)

            /*if (isChecked) {
                val zones = input.details.flexiZones.toInt()

                if (input.details.rowsColumns[0].isFlexi.toBoolean() && input.details.rowsColumns[3].isFlexi.toBoolean()) {
                    mainConstraintLayout.removeView(r1c1)
                    mainConstraintLayout.removeView(r2c1)

                    flexR1C1.id = View.generateViewId()
                    flexR1C1.setPadding(24)
                    flexR1C1.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources, getCircleRectFromType(
                                input.details.rowsColumns[0].isFlexi.toBoolean(),
                                input.details.rowsColumns[0].isCircle.toBoolean(),
                                0,
                                input.details.rowsColumns[0].circleType
                            ), theme
                        )
                    )
                    mainConstraintLayout.addView(flexR1C1)

                    constraintSet.constrainWidth(flexR1C1.id, ConstraintSet.MATCH_CONSTRAINT)
                    constraintSet.constrainHeight(flexR1C1.id, ConstraintSet.MATCH_CONSTRAINT)

                    constraintSet.connect(
                        flexR1C1.id,
                        ConstraintSet.START,
                        mainConstraintLayout.id,
                        ConstraintSet.START,
                        32
                    )
                    constraintSet.connect(
                        flexR1C1.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        32
                    )

                    constraintSet.connect(
                        flexR1C1.id,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM,
                        32
                    )

                    constraintSet.connect(
                        flexR1C1.id, ConstraintSet.END, iv.id, ConstraintSet.START, 32
                    )

                } else {
                    //input.details.rowsColumns[2].isFlexi.toBoolean() && input.details.rowsColumns[5].isFlexi.toBoolean()

                    mainConstraintLayout.removeView(r1c3)
                    mainConstraintLayout.removeView(r2c3)

                    flexR1C3.id = View.generateViewId()
                    flexR1C3.setPadding(24)
                    flexR1C3.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources, getCircleRectFromType(
                                input.details.rowsColumns[2].isFlexi.toBoolean(),
                                input.details.rowsColumns[2].isCircle.toBoolean(),
                                2,
                                input.details.rowsColumns[2].circleType
                            ), theme
                        )
                    )
                    mainConstraintLayout.addView(flexR1C3)

                    constraintSet.constrainWidth(flexR1C3.id, ConstraintSet.WRAP_CONTENT)
                    constraintSet.constrainHeight(flexR1C3.id, ConstraintSet.MATCH_CONSTRAINT)

                    constraintSet.connect(
                        flexR1C3.id,
                        ConstraintSet.END,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.END,
                        32
                    )
                    constraintSet.connect(
                        flexR1C3.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        32
                    )

                    constraintSet.connect(
                        flexR1C3.id,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM,
                        32
                    )

                    constraintSet.connect(
                        flexR1C3.id, ConstraintSet.START, iv.id, ConstraintSet.END, 32
                    )
                }

            } else {
                if (input.details.rowsColumns[0].isFlexi.toBoolean() && input.details.rowsColumns[3].isFlexi.toBoolean()) {
                    mainConstraintLayout.removeView(flexR1C1)
                    mainConstraintLayout.addView(r1c1)
                    mainConstraintLayout.addView(r2c1)
                } else {
                    mainConstraintLayout.removeView(flexR1C3)
                    mainConstraintLayout.addView(r1c3)
                    mainConstraintLayout.addView(r2c3)
                }
            }*/

            /*ivWhirlpoolLogo.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                if (r2c1.isAttachedToWindow) {
                    constraintSet.connect(
                        ivWhirlpoolLogo.id, ConstraintSet.TOP, r2c1.id, ConstraintSet.BOTTOM, 32
                    )
                    this.topToBottom = r2c1.id
                } else if (flexR1C1.isAttachedToWindow) {

                    constraintSet.connect(
                        ivWhirlpoolLogo.id, ConstraintSet.TOP, flexR1C1.id, ConstraintSet.BOTTOM, 32
                    )
                    this.topToBottom = flexR1C1.id
                } else if (r2c3.isAttachedToWindow) {
                    constraintSet.connect(
                        ivWhirlpoolLogo.id, ConstraintSet.TOP, r2c3.id, ConstraintSet.BOTTOM, 32
                    )
                    this.topToBottom = r2c3.id
                } else if(flexR1C3.isAttachedToWindow) {
                    constraintSet.connect(
                        ivWhirlpoolLogo.id, ConstraintSet.TOP, flexR1C3.id, ConstraintSet.BOTTOM, 32
                    )
                    this.topToBottom = flexR1C3.id
                } else{
                    constraintSet.connect(
                        ivWhirlpoolLogo.id, ConstraintSet.TOP, iv.id, ConstraintSet.BOTTOM, 32
                    )
                    this.topToBottom = iv.id
                }
            }*/
        }

        //for whirlpool logo
        /*ivWhirlpoolLogo.id = View.generateViewId()
        ivWhirlpoolLogo.setPadding(24)
        ivWhirlpoolLogo.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources, R.drawable.cooktop_whirlpool_logo, theme
            )
        )
        mainConstraintLayout.addView(ivWhirlpoolLogo)

        constraintSet.constrainWidth(ivWhirlpoolLogo.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(ivWhirlpoolLogo.id, ConstraintSet.WRAP_CONTENT)

        constraintSet.connect(
            ivWhirlpoolLogo.id, ConstraintSet.TOP, r2c1.id, ConstraintSet.BOTTOM, 32
        )
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
        )*/

        constraintSet.applyTo(mainConstraintLayout)
        binding.cvCookTopCard.addView(mainConstraintLayout)
    }

    private fun createSurfaceView(){

    }
}