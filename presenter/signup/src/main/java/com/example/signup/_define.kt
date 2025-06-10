package com.example.signup

const val NAV_TERM = "term"
const val NAV_PHONE = "phone"
const val NAV_NAME = "name"
const val NAV_NICK = "nick"
const val NAV_INFO = "info"
const val NAV_LOGIN = "Login"
const val NAV_MAIN = "Main"
const val NAV_ALREADY_SIGN_UP = "alreadySignUp"
const val ERROR_ALREADY_SIGN_UP = "Already SignUp"
const val ERROR_NETWORK_ERROR = "network error"
const val ERROR_DATA_NOT_INPUT ="Data is Not input"
const val ERROR_SAVE_DATA_FAIL = "Save Fail"
const val ERROR_SAVE_SIGNUP_HISTORY_FAIL ="Save SignUp Result"
const val MALE = "MALE"
const val FEMALE = "FEMALE"
const val NON_SEX = "NONE_SEX"
const val ISYear = "isYear"
const val ISDay = "isDay"
const val ISMonth = "Month"
const val ISCity = "city"
const val ISArea = "area"
const val NAME_OKAY = "name okay"
const val NAME_LONG = "to long"
const val NAME_SHORT = "to short"
const val NAME_SPECIAL = "no special"
const val NICKNAME_ALREADY_USE = "Already use"
const val RE_LOGIN = "reLogin"
const val NICKNAME_UPDATE_SUCCESS = "nick name update Success"

const val TERM_SERVICE_PERMISSION =1
const val TERM_PRIVATE_PERMISSION = 2
const val TERM_MARKET_PERMISSION = 3
enum class City(val displayName: String) {
    SEOUL("서울"),
    BUSAN("부산"),
    DAEGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    GYEONGGI("경기도"),
    GANGWONDO("강원도"),
    CHUNGSHEONGBUKDO("충청북도"),
    CHUNGCHEONGNAMDO("충청남도"),
    JEOLLABUKDO("전북특별자치도"),
    JEOLLANAMDO("전라남도"),
    GYEONGSANGBUKDO("경상북도"),
    GYEONGSANGNAMDO("경상남도"),
    JEJU("제주도");

    companion object {
        fun fromDisplayName(displayName: String): City? {
            return entries.find { it.displayName == displayName }
        }
    }

}

enum class County(val displayName: String) {
    // 서울특별시
    JONGNO("종로구"),
    JUNGGU("중구"),
    YONGSAN("용산구"),
    SEONGDONG("성동구"),
    GWANGJIN("광진구"),
    DDM("동대문구"),
    JUNGNANG("중랑구"),
    SEONGBUK("성북구"),
    GANGBUK("강북구"),
    DOBONG("도봉구"),
    NOWON("노원구"),
    EUNPYEONG("은평구"),
    SDM("서대문구"),
    MAPO("마포구"),
    YANGCHEON("양천구"),
    GANGSEO("강서구"),
    GURO("구로구"),
    GEUMCHEON("금천구"),
    YDP("영등포구"),
    DONGJAK("동작구"),
    GWANAK("관악구"),
    SEOCHO("서초구"),
    GANGNAM("강남구"),
    SONGPA("송파구"),
    GANGDONG("강동구"),

    // 부산광역시
    DONG_GU("동구"),
    YEONGDO("영도구"),
    BUSANJIN("부산진구"),
    DONGNAE("동래구"),
    HAEUNDAE("해운대구"),
    SAHA("사하구"),
    GEUMJEONG("금정구"),
    YEONJE("연제구"),
    SUYEONG("수영구"),
    SASANG("사상구"),
    GIJANG("기장군"),

    // 대구광역시
    NAM("남구"),
    BUK("북구"),
    SUSEONG("수성구"),
    DALSEO("달서구"),
    DALSEONG("달성군"),
    GUNWI("군위군"),


    // 인천광역시
    JUNG("중구"),
    DONG("동구"),
    MICHUHOL("미추홀구"),
    YEONSU("연수구"),
    NAMDONG("남동구"),
    BUPYEONG("부평구"),
    GYEYANG("계양구"),
    SEO("서구"),
    GANGHWA("강화군"),
    ONGJIN("옹진군"),

    // 광주광역시
    GWANGSAN_GU("광산구"),

    // 대전광역시
    YUSEONG("유성구"),
    DAEDEOK("대덕구"),

    // 울산광역시
    ULJU("울주군"),

    // 경기도
    SUWON("수원시"),
    SEONGNAM("성남시"),
    ANYANG("안양시"),
    BUCHEON("부천시"),
    GWANGMYEONG("광명시"),
    PYEONGTAEK("평택시"),
    DONGDUCHEON("동두천시"),
    ANSAN("안산시"),
    GOYANG("고양시"),
    GWACHEON("과천시"),
    UIJEONGBU("의정부시"),
    HANAM("하남시"),
    YONGIN("용인시"),
    POCHEON("포천시"),
    UIWANG("의왕시"),
    OSAN("오산시"),
    GWANGJU("광주시"),
    GURI("구리시"),
    NAMYANGJU("남양주시"),
    ICHEON("이천시"),
    ANSEONG("안성시"),
    GIMPO("김포시"),
    HWASEONG("화성시"),
    SIHEUNG("시흥시"),
    PAJU("파주시"),
    YANGJU("양주시"),
    GUNPO("군포시"),
    GWANGMYEONG_SI("광명시"),
    YEONCHEON("연천군"),
    GAPYEONG("가평군"),
    YANGPYEONG("양평군"),
    YEOJU("여주시"),

    // 강원도
    CHUNCHEON("춘천시"),
    WONJU("원주시"),
    GANGNEUNG("강릉시"),
    DONGHAE("동해시"),
    TAEBAEK("태백시"),
    SAMCHEOK("삼척시"),
    HONGCHEON("홍천군"),
    HOENGSEONG("횡성군"),
    YEONGWOL("영월군"),
    PYEONGCHANG("평창군"),
    JEONGSEON("정선군"),
    CHEORWON("철원군"),
    HWACHEON("화천군"),
    YANGGU("양구군"),
    INJE("인제군"),
    GOSEONG("고성군"),

    //제주도
    JEJU("제주시"),
    SEOGWIPO("서귀포시"),

    // 전북
    JEONJU("전주시"),
    GUNSAN("군산시"),
    IKSAN("익산시"),
    JEONGEUP("정읍시"),
    NAMWON("남원시"),
    GIMJE("김제시"),
    WANJU("완주군"),
    JINAN("진안군"),
    MUJU("무주군"),
    JANGSU("장수군"),
    IMSIL("임실군"),
    SUNCHANG("순창군"),
    GOCHANG("고창군"),
    BUAN("부안군"),

    //전남
    MOKPO("목포시"),
    YEOSU("여수시"),
    SUNCHEON("순천시"),
    NAJU("나주시"),
    GWANGYANG("광양시"),
    DAMYANG("담양군"),
    GOKSEONG("곡성군"),
    GURYE("구례군"),
    GOHEUNG("고흥군"),
    BOSEONG("보성군"),
    HWASUN("화순군"),
    JANGHEUNG("장흥군"),
    GANGJIN("강진군"),
    HAENAM("해남군"),
    YEONGAM("영암군"),
    MUAN("무안군"),
    HAMPYEONG("함평군"),
    YEONGGWANG("영광군"),
    JANGSEONG("장성군"),
    WANDO("완도군"),
    JINDO("진도군"),
    SINAN("신안군"),

    //경북
    POHANG("포항시"),
    GYEONGJU("경주시"),
    GIMCHEON("김천시"),
    ANDONG("안동시"),
    GUMI("구미시"),
    YEONGJU("영주시"),
    YEONGCHEON("영천시"),
    MUNGYEONG("문경시"),
    SANGJU("상주시"),
    GYEONGSAN("경산시"),
    UISEONG("의성군"),
    CHEONGSONG("청송군"),
    YEONGYANG("영양군"),
    YEONGDEOK("영덕군"),
    CHEONGDO("청도군"),
    GORYEONG("고령군"),
    SEONGJU("성주군"),
    CHILGOK("칠곡군"),
    YECHEON("예천군"),
    BONGHWA("봉화군"),
    ULJIN("울진군"),
    ULLEUNG("울릉군"),

    //경남
    CHANGWON("창원시"),
    JINJU("진주시"),
    TONGYEONG("통영시"),
    SACHEON("사천시"),
    GIMHAE("김해시"),
    MILYANG("밀양시"),
    GEOJE("거제시"),
    YANGSAN("양산시"),
    ULRYEONG("의령군"),
    HAMYANG("함안군"),
    CHANGNYEONG("창녕군"),
    NAMHAE("남해군"),
    HADONG("하동군"),
    SANCHEONG("산청군"),
    HAMAN("함양군"),
    GEOCHANG("거창군"),
    HAPCHEON("합천군"),

    // 충청북도
    SANGDANG("상당구"),
    SEOWON("서원구"),
    HEUNGDEOK("흥덕구"),
    CHEONGWON("청원구"),
    CHUNGJU("충주시"),
    JECHEON("제천시"),
    BOEUN("보은군"),
    OKCHEON("옥천군"),
    YEONGDONG("영동군"),
    JEUNGPYEONG("증평군"),
    JINCHEON("진천군"),
    GOESAN("괴산군"),
    EUMSEONG("음성군"),
    DANYANG("단양군"),
    CHEONGJU("청주시"),

    // 충청남도
    CHEONAN("천안시"),
    SEOSAN("서산시"),
    NONSAN("논산시"),
    GYERYONG("계룡시"),
    DANGJIN("당진시"),
    GEUMSAN("금산군"),
    BUYEO("부여군"),
    SEOCHEON("서천군"),
    CHEONGYANG("청양군"),
    HONGSEONG("홍성군"),
    YESAN("예산군"),
    TAEAN("태안군"),
    ASAN("아산시"),
    BORYEONG("보령시"),
    GONGJU("공주시");


    companion object {
        fun fromDisplayName(displayName: String): County? {
            return County.entries.find { it.displayName == displayName }
        }
    }

}

val cityToCountyMap: Map<City, List<County>> = mapOf(
    City.SEOUL to listOf(
        County.JONGNO,
        County.JUNGGU,
        County.YONGSAN,
        County.SEONGDONG,
        County.GWANGJIN,
        County.DDM,
        County.JUNGNANG,
        County.SEONGBUK,
        County.GANGBUK,
        County.DOBONG,
        County.NOWON,
        County.EUNPYEONG,
        County.SDM,
        County.MAPO,
        County.YANGCHEON,
        County.GANGSEO,
        County.GURO,
        County.GEUMCHEON,
        County.YDP,
        County.DONGJAK,
        County.GWANAK,
        County.SEOCHO,
        County.GANGNAM,
        County.SONGPA,
        County.GANGDONG
    ),
    City.BUSAN to listOf(
        County.JUNG,
        County.DONG,
        County.SEO,
        County.NAM,
        County.BUK,
        County.YEONGDO,
        County.BUSANJIN,
        County.DONGNAE,
        County.HAEUNDAE,
        County.SAHA,
        County.GEUMJEONG,
        County.GANGSEO,
        County.YEONJE,
        County.SUYEONG,
        County.SASANG,
        County.GIJANG
    ),

    City.DAEGU to listOf(
        County.JUNG, County.DONG, County.SEO, County.NAM,
        County.BUK, County.SUSEONG, County.DALSEO, County.DALSEONG , County.GUNWI
    ),
    City.INCHEON to listOf(
        County.JUNG, County.DONG, County.MICHUHOL, County.YEONSU,
        County.NAMDONG, County.BUPYEONG, County.GYEYANG, County.SEO,
        County.GANGHWA, County.ONGJIN
    ),
    City.GWANGJU to listOf(
        County.DONG, County.SEO, County.NAM, County.BUK,
        County.GWANGSAN_GU
    ),
    City.DAEJEON to listOf(
        County.DONG, County.JUNG, County.SEO,
        County.YUSEONG, County.DAEDEOK
    ),
    City.ULSAN to listOf(
            County.JUNG , County.DONG , County.NAM , County.BUK , County.ULJU
    ),
    City.CHUNGSHEONGBUKDO to listOf(
        County.CHEONGJU,
        County.SANGDANG,
        County.SEOWON,
        County.HEUNGDEOK,
        County.CHEONGWON,
        County.CHUNGJU,
        County.JECHEON,
        County.BOEUN,
        County.OKCHEON,
        County.YEONGDONG,
        County.JEUNGPYEONG,
        County.JINCHEON,
        County.GOESAN,
        County.EUMSEONG,
        County.DANYANG
    ),
    City.GYEONGGI to listOf(
        County.SUWON,
        County.SEONGNAM,
        County.UIJEONGBU,
        County.ANYANG,
        County.BUCHEON,
        County.GWANGMYEONG_SI,
        County.PYEONGTAEK,
        County.DONGDUCHEON,
        County.ANSAN,
        County.GOYANG,
        County.GWACHEON,
        County.GUNPO,
        County.GURI,
        County.NAMYANGJU,
        County.HANAM,
        County.OSAN,
        County.SIHEUNG,
        County.UIWANG,
        County.YANGJU,
        County.ICHEON,
        County.ANSEONG,
        County.GIMPO,
        County.HWASEONG,
        County.GWANGJU,
        County.YANGPYEONG,
        County.YEOJU,
        County.YEONCHEON,
        County.GAPYEONG,
        County.PAJU,
        County.POCHEON,
        County.YONGIN
    ),
    City.GANGWONDO to listOf(
        County.CHUNCHEON,
        County.WONJU,
        County.GANGNEUNG,
        County.DONGHAE,
        County.TAEBAEK,
        County.SAMCHEOK,
        County.HONGCHEON,
        County.HOENGSEONG,
        County.YEONGWOL,
        County.PYEONGCHANG,
        County.JEONGSEON,
        County.CHEORWON,
        County.HWACHEON,
        County.YANGGU,
        County.INJE,
        County.GOSEONG
    ),
    City.JEOLLABUKDO to listOf(
        County.JEONJU, County.GUNSAN, County.IKSAN, County.JEONGEUP, County.NAMWON,
        County.GIMJE, County.WANJU, County.JINAN, County.MUJU, County.JANGSU,
        County.IMSIL, County.SUNCHANG, County.GOCHANG, County.BUAN
    ),
    City.JEOLLANAMDO to listOf(
        County.MOKPO,
        County.YEOSU,
        County.SUNCHEON,
        County.NAJU,
        County.GWANGYANG,
        County.DAMYANG,
        County.GOKSEONG,
        County.GURYE,
        County.GOHEUNG,
        County.BOSEONG,
        County.HWASUN,
        County.JANGHEUNG,
        County.GANGJIN,
        County.HAENAM,
        County.YEONGAM,
        County.MUAN,
        County.HAMPYEONG,
        County.YEONGGWANG,
        County.JANGSEONG,
        County.WANDO,
        County.JINDO,
        County.SINAN
    ),
    City.GYEONGSANGBUKDO to listOf(
        County.POHANG,
        County.GYEONGJU,
        County.GIMCHEON,
        County.ANDONG,
        County.GUMI,
        County.YEONGJU,
        County.YEONGCHEON,
        County.MUNGYEONG,
        County.SANGJU,
        County.GYEONGSAN,
        County.UISEONG,
        County.CHEONGSONG,
        County.YEONGYANG,
        County.YEONGDEOK,
        County.CHEONGDO,
        County.GORYEONG,
        County.SEONGJU,
        County.CHILGOK,
        County.YECHEON,
        County.BONGHWA,
        County.ULJIN,
        County.ULLEUNG
    ),
    City.GYEONGSANGNAMDO to listOf(
        County.CHANGWON,
        County.JINJU,
        County.TONGYEONG,
        County.SACHEON,
        County.GIMHAE,
        County.MILYANG,
        County.GEOJE,
        County.YANGSAN,
        County.ULRYEONG,
        County.HAMYANG,
        County.CHANGNYEONG,
        County.NAMHAE,
        County.HADONG,
        County.SANCHEONG,
        County.HAMAN,
        County.GEOCHANG,
        County.HAPCHEON,
        County.GOSEONG
    ),
    City.CHUNGCHEONGNAMDO to listOf(
        County.CHEONAN,
        County.SEOSAN,
        County.NONSAN,
        County.GYERYONG,
        County.DANGJIN,
        County.GEUMSAN,
        County.BUYEO,
        County.SEOCHEON,
        County.CHEONGYANG,
        County.HONGSEONG,
        County.YESAN,
        County.TAEAN,
        County.ASAN,
        County.BORYEONG,
        County.GONGJU
    ),

    City.JEJU to listOf(
        County.JEJU, County.SEOGWIPO
    )
)