package com.gavinsappcreations.upcominggames.utilities

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData

/**
 * This is a MutableLiveData that notifies its observers whenever any of its properties change in value.
 */
class PropertyAwareMutableLiveData<T: BaseObservable>(): MutableLiveData<T>() {

    constructor(value: T): this() {
        this.value = value
    }

    private val callback = object: Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            value = value
        }
    }
    override fun setValue(value: T?) {
        super.setValue(value)

        value?.addOnPropertyChangedCallback(callback)
    }
}