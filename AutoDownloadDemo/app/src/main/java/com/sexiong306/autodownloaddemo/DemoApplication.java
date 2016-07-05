/**
 * Copyright (C) 2016 The RDT of Wireless R&D in MIG. All right reversed.
 * <p/>
 * Created by vellhe on 16/4/7.
 */
package com.sexiong306.autodownloaddemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.upgrade.UpgradeListener;

public class DemoApplication extends Application {
    public static final String APP_ID = "900037804"; // TODO 替换成bugly上注册的appid
    public static final String APP_CHANNEL = "DEBUG"; // TODO 自定义渠道

    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        /***** Beta高级设置 *****/
        /**
         * true表示初始化;
         */
        Beta.autoInit = true;

        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 0 * 1000;
        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 0 * 1000;
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.mipmap.ic_launcher;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher;
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.mipmap.ic_launcher;
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        /**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;

        /***** Bugly高级设置 *****/
        BuglyStrategy strategy = new BuglyStrategy();
        /**
         * 设置app渠道号
         */
        strategy.setAppChannel(APP_CHANNEL);


        /**
         * 自动更新下载demo
         */


        Beta.upgradeListener = new UpgradeListener() {
			@Override
			public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
				if (strategy != null) {

                    Beta.startDownload();
                    Beta.registerDownloadListener(new DownloadListener() {
                        @Override
                        public void onReceive(DownloadTask downloadTask) {
                            Log.e("","download " + downloadTask.getSavedLength());
                        }

                        @Override
                        public void onCompleted(DownloadTask downloadTask) {

                        }

                        @Override
                        public void onFailed(DownloadTask downloadTask, int i, String s) {

                        }
                    });
				} else {
					Toast.makeText(DemoApplication.this, "没有更新", Toast.LENGTH_SHORT).show();
				}
			}
		};

        /**
         * true表示app启动自动初始化升级模块; false不会自动初始化;
         * 设置成false，app启动后不会自动检测更新
         */
        Beta.autoCheckUpgrade = false;




        /***** 统一初始化Bugly产品，包含Beta *****/

        Bugly.init(this, APP_ID, true);

        /**
         * 5秒后检测自动更新，也可以在其他地方设置检测更新时间
         */
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Beta.checkUpgrade(false,false);
            }
        },5000);
    }
}
