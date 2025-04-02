package com.example.signup

const val NAV_TERM = "term"
const val NAV_PHONE = "phone"
const val NAV_NAME = "name"
const val NAV_NICK = "nick"
const val NAV_INFO = "info"
const val NAV_LOGIN = "Login"
const val ERROR_ALREADY_SIGN_UP = "Already SignUp"
const val ERROR_TIME_OUT = "Time out"
const val ERROR_FAIL_SMS = "Fail to request SMS"
const val MALE = "male"
const val FEMALE = "female"
const val ISYear = "isYear"
const val ISDay = "isDay"
const val ISMonth = "Month"
const val ISCity = "city"
const val ISArea = "area"
const val STANDBY = "wait"
const val NAME_OKAY = "name okay"
const val NAME_LONG = "to long"
const val NAME_SHORT = "to short"
const val NAME_SPECIAL = "no special"
const val NICKNAME_ALREADY_USE = "Already use"

enum class City(val displayName: String) {
    SEOUL("서울"),
    BUSAN("부산"),
    DAEGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    SEJONG("세종"),
    GYEONGGI("경기도"),
    GANGWON("강원도"),
    CHUNGBUK("충청북도"),
    CHUNGNAM("충청남도"),
    JEONBUK("전북특별자치도"),
    JEONNAM("전라남도"),
    GYEONGBUK("경상북도"),
    GYEONGNAM("경상남도"),
    JEJU("제주도");

    companion object {
        fun fromDisplayName(displayName: String): City? {
            return entries.find { it.displayName == displayName }
        }
    }

}

enum class County(val displayName: String) {
    // 서울특별시
    JONGNO_GU("종로구"),
    JUNG_GU("중구"),
    YONGSAN_GU("용산구"),
    SEONGDONG_GU("성동구"),
    GWANGJIN_GU("광진구"),
    DONGDAEMUN_GU("동대문구"),
    JUNGNANG_GU("중랑구"),
    SEONGBUK_GU("성북구"),
    GANGBUK_GU("강북구"),
    DOBONG_GU("도봉구"),
    NOWON_GU("노원구"),
    EUNPYEONG_GU("은평구"),
    SEODAEMUN_GU("서대문구"),
    MAPO_GU("마포구"),
    YANGCHEON_GU("양천구"),
    GANGSEO_GU("강서구"),
    GURO_GU("구로구"),
    GEUMCHEON_GU("금천구"),
    YEONGDEUNGPO_GU("영등포구"),
    DONGJAK_GU("동작구"),
    GWANAK_GU("관악구"),
    SEOCHO_GU("서초구"),
    GANGNAM_GU("강남구"),
    SONGPA_GU("송파구"),
    GANGDONG_GU("강동구"),

    // 부산광역시
    BUSAN_JUNG_GU("중구"), //-> 빠져있음
    BUSAN_SEO_GU("서구"), // -> 빠져있음
    DONG_GU("동구"),
    YEONGDO_GU("영도구"),
    BUSANJIN_GU("부산진구"),
    DONGNAE_GU("동래구"),
    NAM_GU("남구"),
    BUK_GU("북구"),
    HAEUNDAE_GU("해운대구"),
    SAHA_GU("사하구"),
    GEUMJEONG_GU("금정구"),
    GANGSEO_GU_BUSAN("강서구"),
    YEONJE_GU("연제구"),
    SUYEONG_GU("수영구"),
    SASANG_GU("사상구"),
    GIJANG_GUN("기장군"),

    // 대구광역시
//    JUNG_GU("중구"),
//    DONG_GU("동구"),
    SEO_GU("서구"),
    DAEGU_NAM_GU("남구"),
    DAEGU_BUK_GU("북구"),
    SUSEONG_GU("수성구"),
    DALSEO_GU("달서구"),
    DALSEONG_GUN("달성군"),

    // 인천광역시
    UNG_GU("중구"),
//    DONG_GU("동구"),
    MICHUHOL_GU("미추홀구"),
    YEONSUGU("연수구"),
    NAMDONG_GU("남동구"),
    BUPYEONG_GU("부평구"),
    GYEYANG_GU("계양구"),
//    SEO_GU("서구"),
    GANGHWA_GUN("강화군"),
    ONGJIN_GUN("옹진군"),

    // 광주광역시
//    GWANGJU_DONG_GU("동구"),
//    GWANGJU_SEO_GU("서구"),
//    NAM_GU_GWANGJU("남구"),
//    BUK_GU"북구"),
    GWANGSAN_GU("광산구"),

    // 대전광역시
//    DONG_GU("동구"),
//   JUNG_GU("중구"),
//   SEO_GU("서구"),
    YUSEONG_GU("유성구"),
    DAEDEOK_GU("대덕구"),

    // 울산광역시
//    ULJU_GUN("울주군"),
//    NAM_GU("남구"),
//    DONG_GU("동구"),
//    BUK_GU("북구"),
//    JUNG_GU("중구"),

    // 세종특별자치시
//    SEJONG("세종시"),

    // 경기도
    SUWON_SI("수원시"),
    SEONGNAM_SI("성남시"),
    ANYANG_SI("안양시"),
    BUCHEON_SI("부천시"),
//    GWON_SI("광명시"),
    PYEONGTAEK_SI("평택시"),
    DONGDUCHEON_SI("동두천시"),
    ANSAN_SI("안산시"),
    GOYANG_SI("고양시"),
    GWACHEON_SI("과천시"),
    UIJEONGBU_SI("의정부시"),
    HANAM_SI("하남시"),
    YONGIN_SI("용인시"),
    POCHEON_SI("포천시"),
    UIWANG_SI("의왕시"),
    OSAN_SI("오산시"),
    GWANGJU_SI("광주시"),
    GURI_SI("구리시"),
    NAMYANGJU_SI("남양주시"),
    ICHEON_SI("이천시"),
    ANSEONG_SI("안성시"),
    GIMPO_SI("김포시"),
    HWASEONG_SI("화성시"),
    SIHEUNG_SI("시흥시"),
    PAJU_SI("파주시"),
    YANGJU_SI("양주시"),
    GUNPO_SI("군포시"),
    GWANGMYEONG_SI("광명시"),
    YEONCHEON_GUN("연천군"),
    GAPYEONG_GUN("가평군"),
    YANGPYEONG_GUN("양평군"),
    YEOJU_SI("여주시"),

    //제주도
    JEJU_SI("제주시"),
    SEOGWIPO_SI("서귀포시"),

    // 전북
    JEONJU_SI("전주시"),
    GUNSAN_SI("군산시"),
    IKSAN_SI("익산시"),
    JEONGEUP_SI("정읍시"),
    NAMWON_SI("남원시"),
    GIMJE_SI("김제시"),
    WANJU_GUN("완주군"),
    JINAN_GUN("진안군"),
    MUJU_GUN("무주군"),
    JANGSU_GUN("장수군"),
    IMSIL_GUN("임실군"),
    SUNCHANG_GUN("순창군"),
    GOCHANG_GUN("고창군"),
    BUAN_GUN("부안군"),

    //전남
    MOKPO_SI("목포시"),
    YEOSU_SI("여수시"),
    SUNCHEON_SI("순천시"),
    NAJU_SI("나주시"),
    GWANGYANG_SI("광양시"),
    DAMYANG_GUN("담양군"),
    GOKSEONG_GUN("곡성군"),
    GURYE_GUN("구례군"),
    GOHEUNG_GUN("고흥군"),
    BOSEONG_GUN("보성군"),
    HWASUN_GUN("화순군"),
    JANGHEUNG_GUN("장흥군"),
    GANGJIN_GUN("강진군"),
    HAENAM_GUN("해남군"),
    YEONGAM_GUN("영암군"),
    MUAN_GUN("무안군"),
    HAMPYEONG_GUN("함평군"),
    YEONGGWANG_GUN("영광군"),
    JANGSEONG_GUN("장성군"),
    WANDO_GUN("완도군"),
    JINDO_GUN("진도군"),
    SINAN_GUN("신안군"),

    //경북
    POHANG_SI("포항시"),
    GYEONGJU_SI("경주시"),
    GIMCHEON_SI("김천시"),
    ANDONG_SI("안동시"),
    GUMI_SI("구미시"),
    YEONGJU_SI("영주시"),
    YEONGCHEON_SI("영천시"),
    MUNGYEONG_SI("문경시"),
    SANGJU_SI("상주시"),
    GYEONGSAN_SI("경산시"),
    UISEONG_GUN("의성군"),
    CHEONGSONG_GUN("청송군"),
    YEONGYANG_GUN("영양군"),
    YEONGDEOK_GUN("영덕군"),
    CHEONGDO_GUN("청도군"),
    GORYEONG_GUN("고령군"),
    SEONGJU_GUN("성주군"),
    CHILGOK_GUN("칠곡군"),
    YECHEON_GUN("예천군"),
    BONGHWA_GUN("봉화군"),
    ULJIN_GUN("울진군"),
    ULLEUNG_GUN("울릉군"),

    //경남
    CHANGWON_SI("창원시"),
    JINJU_SI("진주시"),
    TONGYEONG_SI("통영시"),
    SACHEON_SI("사천시"),
    GIMHAE_SI("김해시"),
    MIRYANG_SI("밀양시"),
    GEOJE_SI("거제시"),
    YANGSAN_SI("양산시"),
    UIRYEONG_GUN("의령군"),
    HAMAN_GUN("함안군"),
    CHANGNYEONG_GUN("창녕군"),
    NAMHAE_GUN("남해군"),
    HADONG_GUN("하동군"),
    SANCHEONG_GUN("산청군"),
    HAMYANG_GUN("함양군"),
    GEOCHANG_GUN("거창군"),
    HAPCHEON_GUN("합천군");

    companion object {
        fun fromDisplayName(displayName: String): County? {
            return County.entries.find { it.displayName == displayName }
        }
    }

}

val cityToCountyMap: Map<City, List<County>> = mapOf(
    City.SEOUL to listOf(
        County.JONGNO_GU,
        County.JUNG_GU,
        County.YONGSAN_GU,
        County.SEONGDONG_GU,
        County.GWANGJIN_GU,
        County.DONGDAEMUN_GU,
        County.JUNGNANG_GU,
        County.SEONGBUK_GU,
        County.GANGBUK_GU,
        County.DOBONG_GU,
        County.NOWON_GU,
        County.EUNPYEONG_GU,
        County.SEODAEMUN_GU,
        County.MAPO_GU,
        County.YANGCHEON_GU,
        County.GANGSEO_GU,
        County.GURO_GU,
        County.GEUMCHEON_GU,
        County.YEONGDEUNGPO_GU,
        County.DONGJAK_GU,
        County.GWANAK_GU,
        County.SEOCHO_GU,
        County.GANGNAM_GU,
        County.SONGPA_GU,
        County.GANGDONG_GU
    ),
    City.BUSAN to listOf(
        County.BUSAN_JUNG_GU,
        County.BUSAN_SEO_GU,
        County.DONG_GU,
        County.YEONGDO_GU,
        County.BUSANJIN_GU,
        County.DONGNAE_GU,
        County.NAM_GU,
        County.BUK_GU,
        County.HAEUNDAE_GU,
        County.SAHA_GU,
        County.GEUMJEONG_GU,
        County.GANGSEO_GU_BUSAN,
        County.YEONJE_GU,
        County.SUYEONG_GU,
        County.SASANG_GU,
        County.GIJANG_GUN
    ),
    City.DAEGU to listOf(
        County.JUNG_GU, County.DONG_GU, County.SEO_GU, County.DAEGU_NAM_GU,
        County.DAEGU_BUK_GU, County.SUSEONG_GU, County.DALSEO_GU, County.DALSEONG_GUN
    ),
    City.INCHEON to listOf(
        County.JUNG_GU, County.DONG_GU, County.MICHUHOL_GU, County.YEONSUGU,
        County.NAMDONG_GU, County.BUPYEONG_GU, County.GYEYANG_GU, County.SEO_GU,
        County.GANGHWA_GUN, County.ONGJIN_GUN
    ),
    City.GWANGJU to listOf(
        County.DONG_GU, County.SEO_GU, County.NAM_GU, County.BUK_GU,
        County.GWANGSAN_GU
    ),
    City.DAEJEON to listOf(
        County.DONG_GU, County.JUNG_GU, County.SEO_GU,
        County.YUSEONG_GU, County.DAEDEOK_GU
    ),
    City.ULSAN to listOf(
        County.JUNG_GU, County.NAM_GU, County.DONG_GU, County.BUK_GU,
    ),
//    City.SEJONG to listOf(
//        County.SEJONG
//    ),
    City.GYEONGGI to listOf(
        County.SUWON_SI,
        County.SEONGNAM_SI,
        County.UIJEONGBU_SI,
        County.ANYANG_SI,
        County.BUCHEON_SI,
        County.GWANGMYEONG_SI,
        County.PYEONGTAEK_SI,
        County.DONGDUCHEON_SI,
        County.ANSAN_SI,
        County.GOYANG_SI,
        County.GWACHEON_SI,
        County.GUNPO_SI,
        County.GURI_SI,
        County.NAMYANGJU_SI,
        County.HANAM_SI,
        County.OSAN_SI,
        County.SIHEUNG_SI,
        County.UIWANG_SI,
        County.YANGJU_SI,
        County.ICHEON_SI,
        County.ANSEONG_SI,
        County.GIMPO_SI,
        County.HWASEONG_SI,
        County.GWANGJU_SI,
        County.YANGPYEONG_GUN,
        County.YEOJU_SI,
        County.YEONCHEON_GUN,
        County.GAPYEONG_GUN,
        County.PAJU_SI,
        County.POCHEON_SI,
        County.YONGIN_SI
    ),
    City.JEONBUK to listOf(
        County.JEONJU_SI, County.GUNSAN_SI, County.IKSAN_SI, County.JEONGEUP_SI, County.NAMWON_SI,
        County.GIMJE_SI, County.WANJU_GUN, County.JINAN_GUN, County.MUJU_GUN, County.JANGSU_GUN,
        County.IMSIL_GUN, County.SUNCHANG_GUN, County.GOCHANG_GUN, County.BUAN_GUN
    ),
    City.JEONNAM to listOf(
        County.MOKPO_SI,
        County.YEOSU_SI,
        County.SUNCHEON_SI,
        County.NAJU_SI,
        County.GWANGYANG_SI,
        County.DAMYANG_GUN,
        County.GOKSEONG_GUN,
        County.GURYE_GUN,
        County.GOHEUNG_GUN,
        County.BOSEONG_GUN,
        County.HWASUN_GUN,
        County.JANGHEUNG_GUN,
        County.GANGJIN_GUN,
        County.HAENAM_GUN,
        County.YEONGAM_GUN,
        County.MUAN_GUN,
        County.HAMPYEONG_GUN,
        County.YEONGGWANG_GUN,
        County.JANGSEONG_GUN,
        County.WANDO_GUN,
        County.JINDO_GUN,
        County.SINAN_GUN
    ),
    City.GYEONGBUK to listOf(
        County.POHANG_SI,
        County.GYEONGJU_SI,
        County.GIMCHEON_SI,
        County.ANDONG_SI,
        County.GUMI_SI,
        County.YEONGJU_SI,
        County.YEONGCHEON_SI,
        County.MUNGYEONG_SI,
        County.SANGJU_SI,
        County.GYEONGSAN_SI,
        County.UISEONG_GUN,
        County.CHEONGSONG_GUN,
        County.YEONGYANG_GUN,
        County.YEONGDEOK_GUN,
        County.CHEONGDO_GUN,
        County.GORYEONG_GUN,
        County.SEONGJU_GUN,
        County.CHILGOK_GUN,
        County.YECHEON_GUN,
        County.BONGHWA_GUN,
        County.ULJIN_GUN,
        County.ULLEUNG_GUN
    ),
    City.GYEONGNAM to listOf(
        County.CHANGWON_SI,
        County.JINJU_SI,
        County.TONGYEONG_SI,
        County.SACHEON_SI,
        County.GIMHAE_SI,
        County.MIRYANG_SI,
        County.GEOJE_SI,
        County.YANGSAN_SI,
        County.UIRYEONG_GUN,
        County.HAMAN_GUN,
        County.CHANGNYEONG_GUN,
        County.NAMHAE_GUN,
        County.HADONG_GUN,
        County.SANCHEONG_GUN,
        County.HAMYANG_GUN,
        County.GEOCHANG_GUN,
        County.HAPCHEON_GUN
    ),
    City.JEJU to listOf(
        County.JEJU_SI, County.SEOGWIPO_SI
    )
)