package com.jeremyhaberman.restfulandroid;

/**

 */
public interface OnGetProfileListener {

    public void onSuccess(String name);
    
    public void onError(Exception e);

}
