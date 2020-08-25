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
        const ads:Array<HTMLElement> = [];
        const userid:String = (<HTMLAnchorElement> document.getElementsByClassName("profpic")[0]?.parentElement).href?.split('/')[3]?.split('?')[0] || '';

        document.querySelectorAll('article[data-xt*=sponsor], article[data-store*=sponsor]').forEach(ad => ads.push(<HTMLElement> ad));
        console.log('DETECTED ADS:', ads)
        console.log('USER HASH:', hashCode(userid))
        const adsToSend = ads.filter(x => sentAds.indexOf(x) === -1);
        sentAds = ads;
        console.log('SENT ADS:', adsToSend);
        adsToSend.forEach(ad => Frost.sendAds(ad.dataset.ft || '', hashCode(userid)));
    }
    setInterval(sendAds, 4000);
}).call(undefined);
