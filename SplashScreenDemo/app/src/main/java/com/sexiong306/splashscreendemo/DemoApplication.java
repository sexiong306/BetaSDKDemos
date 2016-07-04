/**
 * Copyright (C) 2016 The RDT of Wireless R&D in MIG. All right reversed.
 * <p/>
 * Created by vellhe on 16/4/7.
 */
package com.sexiong306.splashscreendemo;

import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

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
		 * true表示app启动自动初始化升级模块; false不会自动初始化;
		 * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
		 * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
		 */
		Beta.autoInit = true;

		/**
		 * true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
		 */
		Beta.autoCheckUpgrade = true;

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
		/**
		 * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
		 */
		Beta.canShowUpgradeActs.add(MainActivity.class);

		/***** Bugly高级设置 *****/
		BuglyStrategy strategy = new BuglyStrategy();
		/**
		 * 设置app渠道号
		 */
		strategy.setAppChannel(APP_CHANNEL);

		/**
		 *	设置策略检测回调，监听策略检测的各个阶段，例如：正在检测更新，检测失败，检测成功，没有更新等
		 *	默认回调是弹出toast提示
		 */

		Beta.upgradeStateListener = new UpgradeStateListener() {
			@Override
			public void onUpgradeSuccess(boolean isManual) {
				Toast.makeText(getApplicationContext(),"UPGRADE_SUCCESS",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onUpgradeFailed(boolean isManual) {
				Toast.makeText(getApplicationContext(),"UPGRADE_FAILED",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onUpgrading(boolean isManual) {
				Toast.makeText(getApplicationContext(),"UPGRADE_CHECKING",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onUpgradeNoVersion(boolean isManual) {
				Toast.makeText(getApplicationContext(),"UPGRADE_NO_VERSION",Toast.LENGTH_SHORT).show();
			}
		};


		/**
		 * 设置可以显示更新提示的页面，
		 * 只有在这些页面中才会提示更新.
		 */
		Beta.canShowUpgradeActs.add(MainActivity.class);

		/**
		 * 设置不能显示更新提示的页面，
		 * 在这些页面中不会提示更新.
		 */
		Beta.canNotShowUpgradeActs.add(LoginActivity.class);



		/***** 统一初始化Bugly产品，包含Beta *****/

		Bugly.init(this, APP_ID, true);
	}
}
