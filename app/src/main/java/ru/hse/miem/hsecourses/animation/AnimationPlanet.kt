package ru.hse.miem.hsecourses.animation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class AnimationPlanet : View {

    var viewWidth: Float = 0F//Background width
    var viewHeight: Float = 0F//Background height
    var perIndex: Float = 0F// Current coordinates
    var perIndexInAll: Float = 0F// Current coordinates
    var baseR = 200F
    var viewBackgroundColor = 0x310062.toInt()//Background color
    val C = 0.552284749831f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val heitht = View.MeasureSpec.getSize(heightMeasureSpec)
//Set the current background height and width for the entire component
        if (viewWidth == 0F) {
            viewWidth = width.toFloat()
        }
        if (viewHeight == 0F) {
            viewHeight = heitht.toFloat()
        }
        setMeasuredDimension(width, heitht)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.translate(
            width / 2F,
            height / 2F
        ) // Move the coordinate system to the center of the canvas

//Draw stars in the background
        drawStarts(canvas, perIndexInAll)
//Map the outer gas layer of the planet
        drawGas(canvas, perIndex)
//Draw a planet
        drawPlanet(canvas, perIndex)
    }

    private fun drawStarts(canvas: Canvas, perIndexInAll: Float) {
//The stars in the background appear randomly within a certain range near the planet
        val maxRand = 800

        canvas.translate(-maxRand / 2F, -maxRand / 2F)
        val Random = Random(perIndexInAll.toInt().toLong())

//Draw stars in the background
        for (index in 0..4) {
            drawStart(canvas, Random.nextFloat() * maxRand, Random.nextFloat() * maxRand, perIndex)
        }

        canvas.translate(maxRand / 2F, maxRand / 2F)
    }

    //Draw the content of the stars in the background
    private fun drawStart(canvas: Canvas, x: Float, y: Float, per: Float) {
        var per = per
//This part is to allow the stars to change from small to large and then from large to small
        if (per >= 1.0F) {
            per -= 1F
        }
        if (per <= 0.5F) {
            per *= 2
        } else {
            per = (1 - per) * 2
        }

        canvas.save()
        canvas.translate(x, y)

        canvas.scale(per, per)

        val paint = Paint()
        paint.color = 0xff78D8DF.toInt()

        val startLength = 30F
        val startOffset = startLength / 3F

//Draw the shape of the stars through the path
        val path = Path()
        path.moveTo(0F, startLength)
        path.lineTo(startOffset, startOffset)
        path.lineTo(startLength, 0F)
        path.lineTo(startOffset, -startOffset)
        path.lineTo(0F, -startLength)
        path.lineTo(-startOffset, -startOffset)
        path.lineTo(-startLength, 0F)
        path.lineTo(-startOffset, startOffset)
        path.lineTo(0F, startLength)

        canvas.drawPath(path, paint)

        paint.color = viewBackgroundColor
//Draw the internal shape of the star by zooming out
        canvas.scale(0.3F, 0.3F)
        canvas.drawPath(path, paint)

        canvas.restore()
    }

    private fun drawGas(canvas: Canvas, index: Float) {
        canvas.save()
        canvas.rotate(45F)

        val gasWidth = 18F
        val baseR = baseR * 0.7F
        val absBaseR = baseR / 5F

        val paint = Paint()
        paint.strokeWidth = gasWidth
        paint.style = Paint.Style.STROKE
        paint.color = 0xff2F3768.toInt()

        val paintArc = Paint()
        paintArc.color = 0xff2F3768.toInt()

        val gasLength = baseR * 2F
        canvas.save()

        val gsaL = gasWidth / 2F * 3
        var maxGasLength = (gasLength + gsaL) / 2
        var index = index

        canvas.scale(1F, -1F)

//Map the air flow behind the planet
//Can't bear so many defined variables
//I don't want to write a function with many parameters, so I implemented it like this
        canvas.save()
        canvas.translate(baseR, baseR * 1.2F)
        canvas.translate(0F, absBaseR)
        drawLines(0F, maxGasLength, canvas, paint)
        drawWhite(maxGasLength * index, gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index - 1) * 1.1F, gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index + 1) * 1.1F, gasWidth, gsaL * 2, canvas)
        canvas.restore()

        index = index + 0.3F
        canvas.save()
        canvas.translate(-baseR, baseR * 1.2F)
        canvas.translate(0F, absBaseR)
        drawLines(0F, maxGasLength, canvas, paint)
        drawWhite(maxGasLength * index, gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index - 1), gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index + 1), gasWidth, gsaL * 2, canvas)
        canvas.restore()

        index = index + 0.3F
        canvas.save()
        canvas.translate(0F, baseR * 1.2F)
        canvas.translate(0F, -absBaseR)
        maxGasLength = 2 * absBaseR + maxGasLength
        drawLines(0F, maxGasLength, canvas, paint)
        drawWhite(maxGasLength * index, gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index - 1), gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index + 1), gasWidth, gsaL * 2, canvas)
        canvas.restore()

        index = index + 0.3F
        canvas.save()
        canvas.translate(baseR / 2F, baseR * 1.2F)
        canvas.translate(0F, -absBaseR)
        drawLines(0F, maxGasLength, canvas, paint)
        drawWhite(maxGasLength * index, gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index - 1), gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index + 1), gasWidth, gsaL * 2, canvas)
        canvas.restore()

        index = index + 0.3F
        canvas.save()
        canvas.translate(-baseR / 2F, baseR * 1.2F)
        canvas.translate(0F, -absBaseR)
        drawLines(0F, maxGasLength, canvas, paint)
        drawWhite(maxGasLength * index, gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index - 1), gasWidth, gsaL * 2, canvas)
        drawWhite(maxGasLength * (index + 1), gasWidth, gsaL * 2, canvas)
        canvas.restore()
        canvas.restore()

        val rectArc = RectF(-gasWidth / 2F, -gasWidth / 2F, gasWidth / 2F, gasWidth / 2F)
        canvas.save()
        canvas.translate(baseR, -baseR)
        canvas.drawArc(rectArc, 0F, 360F, false, paintArc)
        canvas.translate(2 * -baseR, 0F)
        canvas.drawArc(rectArc, 0F, 360F, false, paintArc)
        canvas.restore()

        val rectf = RectF(-baseR, -baseR, baseR, baseR)
        canvas.drawArc(rectf, 0F, 180F, false, paint)

        canvas.drawLine(baseR, 0F, baseR, -baseR, paint)
        canvas.drawLine(-baseR, 0F, -baseR, -baseR, paint)

        canvas.restore()
    }

    //Draw the blank part of the tail
    private fun drawWhite(offset: Float, gasWidth: Float, gsaL: Float, canvas: Canvas) {
        val r = gasWidth / 2F

        canvas.save()
        canvas.translate(0F, offset - 2 * gsaL)

        val pointPaint = Paint()
        pointPaint.strokeWidth = 20F
        pointPaint.color = Color.RED

//Draw a semicircle effect through a Bezier curve
        val path = Path()
        path.moveTo(-r, gsaL)
        path.cubicTo(
            -r * C, gsaL - r,
            r * C, gsaL - r,
            r, gsaL
        )

        path.lineTo(r, -gsaL)
        path.cubicTo(
            r * C, -gsaL + r,
            -r * C, -gsaL + r,
            -r, -gsaL
        )

        path.lineTo(-r, gsaL * 1.5F)

        val paint = Paint()
        paint.color = viewBackgroundColor
        canvas.drawPath(path, paint)

        canvas.restore()
    }

    private fun drawLines(index0: Float, index1: Float, canvas: Canvas, paint: Paint) {
        canvas.save()
        val paintArc = Paint()
        paintArc.color = 0xff2F3768.toInt()

        val gasWidth = 18F

        val paint = Paint()
        paint.strokeWidth = gasWidth
        paint.style = Paint.Style.STROKE
        paint.color = 0xff2F3768.toInt()

        val rectArc = RectF(-gasWidth / 2F, -gasWidth / 2F, gasWidth / 2F, gasWidth / 2F)
        canvas.translate(0F, index0)
        canvas.drawArc(rectArc, 0F, 360F, true, paintArc)
        canvas.restore()
        canvas.save()
        canvas.translate(0F, index1)
        canvas.drawArc(rectArc, 0F, 360F, true, paintArc)
        canvas.restore()

        canvas.drawLine(0F, index0, 0F, index1, paint)

    }

    private fun drawPlanet(canvas: Canvas, index: Float) {
//Set the original layer
        val srcB = makeSrc(index)
//Set the mask layer
        val dstB = makeDst(index)

        val paint = Paint()
        canvas.saveLayer(-baseR, -baseR, baseR, baseR, null, Canvas.ALL_SAVE_FLAG)
//Draw mask layer
        canvas.drawBitmap(dstB, -baseR / 2F, -baseR / 2F, paint)
//Set the mask mode to SRC_IN to display the part of the original layer where the original layer intersects the mask layer
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(srcB, width / -2F, height / -2F, paint)
        paint.xfermode = null
    }

    //Set the mask layer
//Set a planet-style mask layer
    fun makeDst(index :Float): Bitmap {
        val bm = Bitmap.createBitmap(baseR.toInt(), baseR.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        canvas.translate(baseR / 2F , baseR / 2F)

        val paint = Paint()
        paint.strokeWidth = 100F
        paint.color = Color.YELLOW

        val rectf = RectF(-baseR / 2F, -baseR / 2F, baseR / 2F, baseR / 2F)
        canvas.drawArc(rectf , 0F , 360F , true, paint)
        return bm
    }

    //Set the source layer
    fun makeSrc(index :Float): Bitmap {
        val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        canvas.translate(width.toFloat() / 2F , height.toFloat() / 2F)

        val paint = Paint()
        paint.color = 0xff57BEC6.toInt()
        paint.style = Paint.Style.FILL

        val rectf = RectF(-baseR / 2F, -baseR / 2F, baseR / 2F, baseR / 2F)
        canvas.drawArc(rectf , 0F , 360F , true , paint)

        canvas.save()


        //Draw planet background
        paint.color = 0xff78D7DE.toInt()
        var baseR = baseR * 0.9.toFloat()
        val rectf2 = RectF(-baseR / 2F, -baseR / 2F, baseR / 2F, baseR / 2F)
        canvas.translate(baseR / 6F , baseR / 6F)
        canvas.drawArc(rectf2 , 0F , 360F , true , paint)

        canvas.restore()
        canvas.rotate(-45F)
        canvas.save()

        val bottomBaseR = baseR / 0.9F / 2
        val path = Path()
        path.moveTo(-bottomBaseR , 0F)
        path.cubicTo(-bottomBaseR , bottomBaseR * 2, bottomBaseR  , bottomBaseR * 2, bottomBaseR , 0F)

        path.cubicTo(
            bottomBaseR * C,bottomBaseR ,
            -bottomBaseR * C,bottomBaseR ,
            -bottomBaseR , 0F
        )

        //Draw the shadow effect of the planet background
        paint.color = 0xffAAEEF2.toInt()
        paint.style = Paint.Style.FILL
        canvas.drawPath(path , paint)

        //Map the landform of the planet
        drawPoints(index , canvas)

        canvas.restore()

        paint.strokeWidth = 30F
        paint.color = 0xff2F3768.toInt()
        paint.style = Paint.Style.STROKE
        canvas.drawArc(rectf , 0F , 360F , true , paint)

        return bm
    }

    private fun drawPoints(index: Float, canvas: Canvas) {
        val paintB = Paint()
        val paintS = Paint()
        paintS.style = Paint.Style.FILL
        paintS.color = 0xffE7F2FB.toInt()

        paintB.style = Paint.Style.FILL
        paintB.color = 0xff2F3768.toInt()

        val baseRB = baseR / 2F / 3
        val baseRS = baseR / 2F / 3 / 3

        val rectfB = RectF(-baseRB, -baseRB, baseRB, baseRB)
        val rectfS = RectF(-baseRS, -baseRS, baseRS, baseRS)

        val pointPaint = Paint()
        pointPaint.color = Color.BLACK
        pointPaint.strokeWidth = 50F

        val coverWidth = baseR

        //Simulate the rotation effect of the planet by moving the coordinate origin
        canvas.translate(-coverWidth / 2F , coverWidth * 1.5F)

        canvas.translate(0F , coverWidth * index )

        //Repeatedly draw the landform of the planet three times so that the rotation of the planet is seamlessly connected
        for (i in 0..2){
            canvas.save()
            canvas.translate(coverWidth / 3F / 2  , -coverWidth / 3F * 2)
            canvas.drawArc(rectfB , 0F , 360F , true , paintB)
            canvas.drawArc(rectfS , 0F , 360F , true , paintS)
            canvas.restore()

            canvas.save()
            canvas.translate(coverWidth / 3F *2 , -coverWidth / 3F)
            canvas.drawArc(rectfB , 0F , 360F , true , paintB)
            canvas.drawArc(rectfS , 0F , 360F , true , paintS)
            canvas.restore()

            canvas.save()
            canvas.translate(coverWidth / 3F *2 , -coverWidth / 8F * 7 + -coverWidth / 10F )
            canvas.drawArc(rectfS , 0F , 360F , true , paintB)
            canvas.restore()

            canvas.save()
            canvas.translate(coverWidth / 3F *2 , -coverWidth / 8F * 7  - -coverWidth / 10F )
            canvas.drawArc(rectfS , 0F , 360F , true , paintB)
            canvas.restore()

            canvas.translate(0F , -coverWidth)
        }
    }


    //Start animation
    fun changeView() {
        val va = ValueAnimator.ofFloat(0F, 50F)
        va.duration = 50000
//        va.interpolator = OvershootInterpolator()
        va.addUpdateListener { animation ->
            perIndex = animation.animatedValue as Float
            perIndexInAll = perIndex
            //Take the data after the decimal point during the loop
            perIndex = perIndex * 10 % 10 / 10F
            invalidate()
        }
        va.start()
    }



}