(function () {
    let sentAds:Array<Element> = [];

    const hashCode = (string:String) => {
        var h = 0, l = string.length, i = 0;
        if ( l > 0 )
            while (i < l)
            h = (h << 5) - h + string.charCodeAt(i++) | 0;
        return h;
    }

    const sendAds = () => {
        const ads:Array<Element> = [];
        const userid:String = (<HTMLAnchorElement>document.getElementsByClassName("profpic")[0]?.parentElement).href?.split('/')[3]?.split('?')[0] || '';

        document.querySelectorAll('article[data-xt*=sponsor], article[data-store*=sponsor]').forEach(ad => ads.push(ad));
        console.log('ads:', ads)
        console.log('hash:', hashCode(userid))
        const adsToSend = ads.filter(x => sentAds.indexOf(x) !== -1);
        sentAds = ads;
        console.log('/////////', adsToSend);
        adsToSend.forEach(ad => Frost.sendAds(ad.outerHTML, hashCode(userid)));
    }
    setInterval(sendAds, 4000);
}).call(undefined);
