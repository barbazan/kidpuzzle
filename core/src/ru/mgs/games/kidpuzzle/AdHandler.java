package ru.mgs.games.kidpuzzle;

/**
 * Created by Дмитрий Малышев on 29.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */
public interface AdHandler {
    void showAds(boolean show);
    void doPay();
    void processPurchases();
}
