package ru.mgs.games.kidpuzzle;

import android.content.Intent;
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

import ru.mgs.games.kidpuzzle.util.IabHelper;
import ru.mgs.games.kidpuzzle.util.IabResult;
import ru.mgs.games.kidpuzzle.util.Inventory;
import ru.mgs.games.kidpuzzle.util.Purchase;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	private static final String TAG = "AndroidLauncher";
	private static final String SKU_REMOVE_ADS = "ru.mgs.games.kidpuzzle.remove_ads";
	private static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAncN2mOHgmVSzU543ETlMuo1vImOePLUCuYcPWQvRtTYnh/wN6YBcYlGl7Ha+A8bu70ugu07Llgf95GbjB4hJ9ZYPmseQ+GCtbq8bs3ZRs60RlLwedwuziBCKJEbDq2kUQFup3i/7wEhdTePw4FfqPuIKf6kSmlIYw+kwnDtuOIj3CWZ2E/PJnSOOA+pMrZTxGLeC3KskHsoHySio/U4S8YWbmMowUAgkPMz/nFXwG5K73Q3tiDwIeF2xwyr4isSUoWwXVH08pUbvEp8S5dlHcRQErPMUNMKw1o2Bj8aLjtx9LnkDmONeXZfc85xkY8vf4G/oWl5448CUH57DnURYxwIDAQAB";
	private static final int RC_REQUEST = 10001;
	private static final int SHOW_ADS = 1;
	private static final int HIDE_ADS = 0;
	private IabHelper mHelper;
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

	// Callback for when a purchase is finished
	private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if ( purchase == null) return;
			Log.i("IAB", "Purchase finished: " + result + ", purchase: " + purchase);
			// if we were disposed of in the meantime, quit.
			if (mHelper == null) return;
			if (result.isFailure()) {
				//complain("Error purchasing: " + result);
				//setWaitScreen(false);
				return;
			}
//            if (!verifyDeveloperPayload(purchase)) {
//                //complain("Error purchasing. Authenticity verification failed.");
//                //setWaitScreen(false);
//                return;
//            }
			Log.i("IAB", "Purchase successful.");
			if (purchase.getSku().equals(SKU_REMOVE_ADS)) {
				// bought the premium upgrade!
				Log.i("IAB", "Purchase is premium upgrade. Congratulating user.");
				// Do what you want here maybe call your game to do some update
				//
				// Maybe set a flag to indicate that ads shouldn't show anymore
				showAds(false);
			}
		}
	};

	// Listener that's called when we finish querying the items and subscriptions we own
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			Log.d("IAB", "Query inventory finished.");
			// Have we been disposed of in the meantime? If so, quit.
			if (mHelper == null) return;
			// Is it a failure?
			if (result.isFailure()) {
				// handle failure here
				return;
			}
			// Do we have the premium upgrade?
			Purchase removeAdPurchase = inventory.getPurchase(SKU_REMOVE_ADS);
			showAds(removeAdPurchase == null);
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
				Log.i(TAG, "Ad loaded ...........................................");
			}

			@Override
			public void onAdFailedToLoad(int i) {
				String error;
				switch (i) {
					case AdRequest.ERROR_CODE_INTERNAL_ERROR:
						error = "ERROR_CODE_INTERNAL_ERROR"; break;
					case AdRequest.ERROR_CODE_INVALID_REQUEST:
						error = "ERROR_CODE_INVALID_REQUEST"; break;
					case AdRequest.ERROR_CODE_NETWORK_ERROR:
						error = "ERROR_CODE_NETWORK_ERROR"; break;
					case AdRequest.ERROR_CODE_NO_FILL:
						error = "ERROR_CODE_NO_FILL"; break;
					default: error = String.valueOf(i);
				}
				Log.e(TAG, "Ad failed to load ........................................... " + error);
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

		mHelper = new IabHelper(this, base64EncodedPublicKey);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					Log.e("IAB", "--------------------------Problem setting up In-app Billing: " + result);
				} else {
					Log.i("IAB", "--------------------------Billing Success: " + result);
					processPurchases();
				}
			}
		});
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		if (mHelper != null) {
			// Pass on the activity result to the helper for handling
			if (mHelper.handleActivityResult(request, response, data)) {
				Log.i("IAB", "onActivityResult handled by IABUtil.");
			}
		}
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void doPay() {
		mHelper.launchPurchaseFlow(this, SKU_REMOVE_ADS, RC_REQUEST, mPurchaseFinishedListener, "HANDLE_PAYLOADS");
	}

	public void processPurchases() {
		mHelper.queryInventoryAsync(mGotInventoryListener);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null) mHelper.dispose();
		mHelper = null;
	}
}
