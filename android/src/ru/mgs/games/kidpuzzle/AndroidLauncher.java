package ru.mgs.games.kidpuzzle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	private static final String TAG = "AndroidLauncher";
	private static final int SHOW_ADS = 1;
	private static final int HIDE_ADS = 0;
	private AdView adView;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.INVISIBLE);
					break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new KidPuzzleGame(this), config);
		layout.addView(gameView);

		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				int visibility = adView.getVisibility();
				adView.setVisibility(AdView.GONE);
				adView.setVisibility(visibility);
				Log.i(TAG, "Ad loaded ...");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(GameConfig.ADMOB_BUNNER_ID);
		AdRequest.Builder builder = new AdRequest.Builder();
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		layout.addView(adView, adParams);
		adView.loadAd(builder.build());

		setContentView(layout);

	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
