package com.aura.githubuser.ujian

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Presenter(view: ListContract.View, elementsProvider: ElementsProvider) {
    val disposables = CompositeDisposable()

    init {
        val disposable = elementsProvider.loadElements()
            .doOnSubscribe { view.showLoading() }
            .doFinally { view.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                view.setElements(result)
            }, { error ->
                view.showError()
            })
//            .subscribe { elements, err ->
//
//            }
        disposables.add(elementsProvider.loadElements()
            .doOnSubscribe { view.showLoading() }
            .doFinally { view.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if(result.size > 0) {
                    view.setElements(result)
                } else {
                    view.showError()
                }
            }, { error ->
                view.showError()
            }))

        /*
        .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(@NonNull Disposable disposable) throws Exception {
                    getView().showLoading(true);
                }
            })
            .doFinally(new Action() {
                @Override
                public void run() throws Exception {
                    getView().showLoading(false);
                }
            })
         */

        view.hideLoading()
    }
}