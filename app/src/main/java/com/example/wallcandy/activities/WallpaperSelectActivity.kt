package com.example.wallcandy.activities

import android.app.Dialog
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.WindowManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.wallcandy.R
import com.example.wallcandy.adapters.WallpaperPagerAdapter
import com.example.wallcandy.databinding.ActivityWallpaperSelectBinding
import com.example.wallcandy.fragments.BottomSheetFragment
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.utilities.MyConstants
import com.example.wallcandy.utilities.MyToast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import eightbitlab.com.blurview.RenderScriptBlur
import java.io.ByteArrayOutputStream

class WallpaperSelectActivity : AppCompatActivity() {
    private var bind : ActivityWallpaperSelectBinding? = null
    private var currWallpaperIndex : Int = 0
    private lateinit var wallpaperList : List<Wallpaper>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        bind = ActivityWallpaperSelectBinding.inflate(layoutInflater)
        setContentView(bind!!.root)

        currWallpaperIndex = intent.getIntExtra(MyConstants.INDEX, 0)
        wallpaperList = intent.getParcelableArrayListExtra<Wallpaper>(MyConstants.LIST) as ArrayList<Wallpaper>

        setupWallpaperPagerAdapter()
        setupViewPagerTransformer()
        setupListeners()
        setBlur()
    }

    private fun setupWallpaperPagerAdapter(){
        val wallpaperPagerAdapter = WallpaperPagerAdapter(wallpaperList, this)
        bind!!.viewPager.adapter = wallpaperPagerAdapter

        bind!!.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        bind!!.viewPager.currentItem = currWallpaperIndex
        bind!!.viewPager.offscreenPageLimit = 3
        bind!!.viewPager.clipToPadding = false
        bind!!.viewPager.clipChildren = false

        Glide.with(this@WallpaperSelectActivity).load(wallpaperList[currWallpaperIndex].src.portrait).centerCrop()
            .into(bind!!.image)
    }

    private fun setupViewPagerTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        bind!!.viewPager.setPageTransformer(transformer)
    }

    private fun setupListeners() {
        bind!!.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Update the current index
                currWallpaperIndex = position
                // Add your code here to handle the selected page change
                Glide.with(this@WallpaperSelectActivity).load(wallpaperList[currWallpaperIndex].src.portrait).centerCrop()
                    .into(bind!!.image)
            }
        })

        bind!!.btnDownload.setOnClickListener {
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
        }
    }

    private val permissionListener = object : PermissionListener{
        override fun onPermissionGranted() {
            openBottomSheet()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            MyToast.showToastShort("Permissions required for respective operations!", this@WallpaperSelectActivity)
        }
    }

    private fun openBottomSheet(){
        var bottomSheetMain = BottomSheetFragment()
        bottomSheetMain.show(supportFragmentManager, "bottomSheetMain")
    }

    private fun setBlur(){
        bind!!.blurView.setupWith(bind!!.root, RenderScriptBlur(this))
            .setBlurRadius(5f)
            .setFrameClearDrawable(window.decorView.background)
            .setBlurAutoUpdate(true)
    }

    fun onSelectOption(option : Int){
        when(option){
            1 -> downloadWallpaper()
            2 -> setHomeScreenWallpaper()
            3 -> setLockScreenWallpaper()
        }
    }

    private fun downloadWallpaper(){
        try {
            val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val imageUrl = Uri.parse(wallpaperList[currWallpaperIndex].src.portrait)
            val request = DownloadManager.Request(imageUrl).apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setMimeType("image/*")
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle("wallpaper_${wallpaperList[currWallpaperIndex].id}.jpg")
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        "/WallCandy/wallpaper_${wallpaperList[currWallpaperIndex].id}.jpg"
                    )
            }
            downloadManager.enqueue(request)
            MyToast.showToastShort( "Downloading...", this)
        }catch (e : Exception){
            MyToast.showToastShort("Download failed!", this)
        }
    }

    private fun setHomeScreenWallpaper(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try{
                val wallpaperManager = WallpaperManager.getInstance(this)

                if(bind!!.image.drawable == null)
                    MyToast.showToastShort("Try again later, Loading...", this)
                else{
                    val bitmap = (bind!!.image.drawable as BitmapDrawable).bitmap
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    MyToast.showToastShort("Wallpaper set on Home Screen", this)
                }

            }catch (e : Exception){

            }
        }
    }

    private fun setLockScreenWallpaper(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try{
                val wallpaperManager = WallpaperManager.getInstance(this)

                if(bind!!.image.drawable == null)
                    MyToast.showToastShort("Try again later, Loading...", this)
                else{
                    val bitmap = (bind!!.image.drawable as BitmapDrawable).bitmap
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    MyToast.showToastShort("Wallpaper set on Lock Screen", this)
                }

            }catch (e : Exception){

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }
}