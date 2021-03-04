package fiordor.fiocca.quotations.threads;

import java.lang.ref.WeakReference;

import fiordor.fiocca.quotations.QuotationActivity;

public class RequestThread extends Thread {

    private WeakReference<QuotationActivity> weakReference;

    public RequestThread(QuotationActivity activity) {
        weakReference = new WeakReference<>(activity);
    }


    @Override
    public void run() {
        if (weakReference.get() == null) return;

        weakReference.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weakReference.get().setLoadingOff(null);
            }
        });
    }
}
