package com.example.moonphases

class Functions {
    fun Trig1(year: Int,month: Int,day: Int): Int {
        var thisJD = julday(year,month,day);
        var degToRad = 3.14159265 / 180;
        var K0 = Math.floor((year-1900)*12.3685);
        var T = (year-1899.5) / 100;
        var T2 = T*T; var T3 = T*T*T;
        var J0 = 2415020 + 29*K0;
        var F0 = 0.0001178*T2 - 0.000000155*T3 + (0.75933 + 0.53058868*K0) - (0.000837*T + 0.000335*T2);
        var M0 = 360*(GetFrac(K0*0.08084821133)) + 359.2242 - 0.0000333*T2 - 0.00000347*T3;
        var M1 = 360*(GetFrac(K0*0.07171366128)) + 306.0253 + 0.0107306*T2 + 0.00001236*T3;
        var B1 = 360*(GetFrac(K0*0.08519585128)) + 21.2964 - (0.0016528*T2) - (0.00000239*T3);
        var phase = 0;
        var jday = 0;
        var oldJ = 0;
        while (jday < thisJD) {
            var F = F0 + 1.530588*phase;
            var M5 = (M0 + phase*29.10535608)*degToRad;
            var M6 = (M1 + phase*385.81691806)*degToRad;
            var B6 = (B1 + phase*390.67050646)*degToRad;
            F -= 0.4068*Math.sin(M6) + (0.1734 - 0.000393*T)*Math.sin(M5);
            F += 0.0161*Math.sin(2*M6) + 0.0104*Math.sin(2*B6);
            F -= 0.0074*Math.sin(M5 - M6) - 0.0051*Math.sin(M5 + M6);
            F += 0.0021*Math.sin(2*M5) + 0.0010*Math.sin(2*B6-M6);
            F += 0.5 / 1440;
            oldJ=jday;
            jday = (J0 + 28*phase + Math.floor(F)).toInt();
            phase++;
        }
        return ((thisJD-oldJ)%30).toInt();
    }
    fun GetFrac(fr: Double): Int {	return ((fr - Math.floor(fr)).toInt());}
}

fun Trig2(year: Int,month: Int,day:Int): Int {
    var n = Math.floor(12.37 * (year -1900 + ((1.0 * month - 0.5)/12.0)))
    var RAD = 3.14159265/180.0
    var t = n / 1236.85
    var t2 = t * t
    var aas = 359.2242 + 29.105356 * n
    var am = 306.0253 + 385.816918 * n + 0.010730 * t2
    var xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2
    xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * aas) - 0.4068 * Math.sin(RAD * am)
    var i: Double
    if (xtra > 0.0)
        i = Math.floor(xtra)
    else
        i = Math.ceil(xtra - 1.0)
    var j1 = julday(year,month,day)
    var jd = (2415020 + 28 * n) + i
    return ((j1-jd + 30)%30).toInt()
}

fun julday(year: Int, month: Int, day:Int): Double {
    var jy = year
    if (jy < 0) { jy ++; }
    var jm = month
    if (month <= 2) {jy--;	jm += 12;	}
    var jul = Math.floor(365.25 *jy) + Math.floor(30.6001 * jm) + day + 1720995
    if (day+31*(month+12*year) >= (15+31*(10+12*1582))) {
        var ja = Math.floor(0.01 * jy)
        jul = jul + 2 - ja + Math.floor(0.25 * ja)
    }
    return jul
}