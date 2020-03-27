package sk3m3l1io.kalymnos.memogame.model;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public class FakePlayerRepository implements PlayerRepository {
    private PlayerDataListener listener;

    @Override
    public void setPlayerDataListener(PlayerDataListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadPlayers() {
        new Thread(() -> {
            final Random r = new Random();
            final List<Player> list = createFakePlayers();
            Collections.sort(list, (p1, p2) -> p2.getScore() - p1.getScore());
            delay(500);
            new Handler(Looper.getMainLooper()).post(() -> listener.onPlayersLoaded(list));
        }).start();
    }

    private List<Player> createFakePlayers() {
        Random r = new Random();
        List<Player> list = new ArrayList<>(30);
        Player p = new Player("Chris", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/90301189_10157123134531589_8649778939725611008_o.jpg?_nc_cat=101&_nc_sid=85a577&_nc_oc=AQnqaSpPZip_j4HUleu-h2Mp9kIBybufk-giiIxndWDHfULhvGBvhRdrr-RC8PVQDo8uD-XlhV2rmcs4i1D7EBFi&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=cbb4a8f576225dbb5e16829da120492a&oe=5EA2BC1D"), r.nextInt(40000));
        list.add(p);
        p = new Player("Panos", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p720x720/87259341_10221259465377361_3745986367226642432_o.jpg?_nc_cat=102&_nc_sid=85a577&_nc_oc=AQl0oxn5u5PX6AOO9KAsSMQ6NfkWhu0BG-vMaDQpK6p-xuWp92hj1g2jVo4pmFfX73xD4NTV4qn94snrFYfdnl_m&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=677f305b11ff5470898561322a8f7ca5&oe=5EA552B2"), r.nextInt(40000));
        list.add(p);
        p = new Player("Kako", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/s960x960/31959874_226545331429720_8197679459399106560_o.jpg?_nc_cat=111&_nc_sid=85a577&_nc_oc=AQk13Mf8m0s0bBLc0ugxczkFhb-3Mn6HBRcB-zoYX7GSqGHQ8NDHrSUKrvMyt1wvhlKvJx19UYgejR-hvZuSIBHC&_nc_ht=scontent-dus1-1.xx&_nc_tp=7&oh=877985a9ac95bd9b6c38d3eedb3ef87a&oe=5EA520F1"), r.nextInt(40000));
        list.add(p);
        p = new Player("Peronosporos", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/12642823_10153915735435948_7350436332242441520_n.jpg?_nc_cat=108&_nc_sid=85a577&_nc_oc=AQkd9fHR0Q3-i7-xr2cFwxL1APv-eVHwRtbrWN7wp7ZfvvHEXq5tlapBeJlDgK3hVd6cnm9MvoCo6t-lhmfafCE8&_nc_ht=scontent-dus1-1.xx&oh=914837d4c7392ed71b8c5ae8881ee85f&oe=5EA1B404"), r.nextInt(40000));
        list.add(p);
        p = new Player("Tsagaris", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/44318036_10216951204029991_7233585882723254272_n.jpg?_nc_cat=109&_nc_sid=85a577&_nc_oc=AQm-P545Z3PYwtuHgScoVG-rZ0hKuFfsW-AyOSfK7k8fipPrQ-g22x18opxdJWqQErxf5TkkcC0Sw1PSq-yKo1Je&_nc_ht=scontent-dus1-1.xx&oh=8ec4438a6b4ae21cebc58117b641afbf&oe=5EA2CE94"), r.nextInt(40000));
        list.add(p);
        p = new Player("Vallas", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/28279084_900488450133314_2931716900083556005_n.jpg?_nc_cat=104&_nc_sid=85a577&_nc_oc=AQn0_fcl3nxurhQvQgA5BMSM5MYHRzawW4PdpbQf_MzYBqHVoNpPiOIs49Tkv4Z7r8E9RIVx8CJXcETjhQUwjXXg&_nc_ht=scontent-dus1-1.xx&oh=a5cb2e829380bc4e78fd1cc0cfa24c4e&oe=5EA224A3"), r.nextInt(40000));
        list.add(p);
        p = new Player("Gio-rgos", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/87358450_2695910423961707_4043479626331193344_n.jpg?_nc_cat=110&_nc_sid=85a577&_nc_oc=AQmoMgRx5m_MuL0UCJqJJpP8AmOk9KSbQOpWpsfRlOgWIU2YwRfb1cIgt1W-4qeTXoN0iK-b_TsYLjhOKLAObweU&_nc_ht=scontent-dus1-1.xx&oh=e7e5f3a55b9c4a515e6972e4d5b3569f&oe=5EA4647A"), r.nextInt(40000));
        list.add(p);
        p = new Player("Kappa Fleez", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-1/c0.3.200.200a/1911962_4043807509710_1507426417_n.jpg?_nc_cat=111&_nc_sid=dbb9e7&_nc_oc=AQkRJn8_w25gEa4S8pxhS0UbcJvAAjSZ8An66CZ_7XNqP_Rkj7twQ9oKFF1UQZdjSPlR6NSuWKd19uxaaWOjyk7o&_nc_ht=scontent-dus1-1.xx&oh=584b377041829f4f9b1b67f0884851aa&oe=5EA50BB9"), r.nextInt(40000));
        list.add(p);
        p = new Player("Olympitis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/59717713_2343376432544341_5397868941462732800_n.jpg?_nc_cat=110&_nc_sid=85a577&_nc_oc=AQlhfY1HTA1x_6_YzdFSBNvWiii0Rnbch_x7NVW-RljGI6dJumSdj6DZKwPhzR_jaYgm2PKt35f7ng42-4h5rdy_&_nc_ht=scontent-dus1-1.xx&oh=b5d24336ef10ba11b0b935a72c9d52fa&oe=5EA28C42"), r.nextInt(40000));
        list.add(p);
        p = new Player("Tolis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/11221833_119546858892940_8551262415776213866_n.jpg?_nc_cat=107&_nc_sid=85a577&_nc_oc=AQlsYUs2svUDQbIVUbf-mW3vrOxeewm4sLyoGr1RKoF8jIMFCrWVvePGQEmk0iqeq2R4JmWeVGXZ6gTx6Rvt3lyv&_nc_ht=scontent-dus1-1.xx&oh=7f3f53ed7debe797dc288213ce195507&oe=5EA1C4F5"), r.nextInt(40000));
        list.add(p);
        p = new Player("Marianna", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t31.0-8/14435383_10207361759138583_7020324881971294691_o.jpg?_nc_cat=101&_nc_sid=85a577&_nc_oc=AQnYP5xtxIRa8eZ82DwLjDmTWz8g50gbYZ8nxZ2_5PYVccLBxMEDwqgw6W9n0BkGyehf8JcBxS_lsK1kZKHkCZ88&_nc_ht=scontent-dus1-1.xx&oh=f706a04bd229172cd7ec955b6cc68105&oe=5EA3011B"), r.nextInt(40000));
        list.add(p);
        p = new Player("Artinopoulos", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/26239491_10213930708798086_5821274374374081291_n.jpg?_nc_cat=100&_nc_sid=85a577&_nc_oc=AQkDydgFd3quyoMTF3eV2g2Q_Oo9lPu6wTme2BEfeV47f_k0yn-ZQNkjGmUwHMByR19m6d7tR0igOLCoTncTIpSy&_nc_ht=scontent-dus1-1.xx&oh=0b53e908c973baac1860ce39ba051374&oe=5EA4B221"), r.nextInt(40000));
        list.add(p);
        p = new Player("Taso", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/40049176_10160814463345344_8387180181072117760_o.jpg?_nc_cat=103&_nc_sid=85a577&_nc_oc=AQmA_Dw0jbkbDYfZxveJ5u6GPZZXE9NCztO61PX2rhadEcwllThroEnzconVSM-n0Tkd7CfB826UrrobP_vGIiqM&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=4262f48ca400cbf92a1cb34298bc4f97&oe=5EA3713D"), r.nextInt(40000));
        list.add(p);
        p = new Player("Karavelidis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/66798979_3019476094736413_5285942849643216896_o.jpg?_nc_cat=108&_nc_sid=85a577&_nc_oc=AQmceZ4HnE4Yrm_IYUE5ilVSJp-AtVxKYcelQwLpjOMVj_QqMonz5_5ezmjxYKI72j1by8aUjvn8C0rle0EWr_G_&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=f6c59f034fa385501fac405eb960d923&oe=5EA3C27F"), r.nextInt(40000));
        list.add(p);
        p = new Player("Pallas", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/89034631_495452827800904_7279015873526890496_n.jpg?_nc_cat=105&_nc_sid=85a577&_nc_oc=AQnudwPr6_rVt-mcCA1aqr3Erc5H7bTjQ4qSTRM9UG8XFC-rm5JrBXEcFosLsKg_7HldnmgNS-rUOOUY4Azb5qqX&_nc_ht=scontent-dus1-1.xx&oh=14c34cdc8f9ae78aaf6e882f4ce432fc&oe=5EA52D2B"), r.nextInt(40000));
        list.add(p);
        p = new Player("Stefanos", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/402303_10205620907946933_5047768860067708286_n.jpg?_nc_cat=101&_nc_sid=85a577&_nc_oc=AQllXIkvp5uvQWP-WQa0qm5lcY287gtmGyJJ_LvobcR7OLphyaBokJU1TFAgKJ_xfp98zDXWTvf0uC9oNoQs-kwD&_nc_ht=scontent-dus1-1.xx&oh=b3d206dbc50a795503bf6c280444a428&oe=5EA1B254"), r.nextInt(40000));
        list.add(p);
        p = new Player("Aggelos", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/43717554_2105974572757493_7716934638857355264_o.jpg?_nc_cat=105&_nc_sid=85a577&_nc_oc=AQm5KzComW8kKBp7uI6XNB2z-2TAnbt68S_OIuQYijyRlrADR3E9wx49jI9lMOT4mixlD9RfWpGn7rWso3ZQG-og&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=b430fa9aa4da4846659637e99ca9f008&oe=5EA21DFF"), r.nextInt(40000));
        list.add(p);
        p = new Player("Magda", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/82307168_10220704261381563_8856378269801381888_n.jpg?_nc_cat=111&_nc_sid=85a577&_nc_oc=AQmLdDB9F8VggDFJ-44cfHc-bs72KBqa53s4Ksqkh-PeYNtQQr_NsWGeXPxE4gRVaqxrASoWxkaZmL-6B7_76ubd&_nc_ht=scontent-dus1-1.xx&oh=f66a903ba6dc64ea6a47c1d0b721fd84&oe=5EA4D3A7"), r.nextInt(40000));
        list.add(p);
        p = new Player("Despoina", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/10417802_10152474568708519_3945154817894533180_n.jpg?_nc_cat=103&_nc_sid=85a577&_nc_oc=AQnpQtQiq6UgbP-GkkWWUN6tQpeXlmoOZSYDG4FgVirv6Gm4s1xw-3pMwfVPlpmJVGchjTXgP09GQs-sK2ewNKgh&_nc_ht=scontent-dus1-1.xx&oh=c401a2d905265bec71450ef25673e302&oe=5EA5615D"), r.nextInt(40000));
        list.add(p);
        p = new Player("St Lospsi", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/89054307_10221440470381736_254168691334184960_o.jpg?_nc_cat=108&_nc_sid=85a577&_nc_oc=AQlYUdr_p7L-mt7df3rH98HNuc74J-s5NLYhqxzgiOrNzhuTkDqjZ33rX14mN5c6QhZ5qj-Xlu_JmzNgzu4c88HF&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=b2a157ac1e7411dbb77923eb6ffb0654&oe=5EA40693"), r.nextInt(40000));
        list.add(p);
        p = new Player("Elena", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t31.0-8/p960x960/21544215_10214265342812348_7289358113092802270_o.jpg?_nc_cat=110&_nc_sid=85a577&_nc_oc=AQkrHIrsxYSabHV2Ib-YrWAlDQM7y7J3-5qVzu80NSBUFcIYKNNXCwWwVh8_ikQScSLsAtD34Auwqkr6WgxerSnT&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=8b5ef12074a366f4f1662318881bd6b6&oe=5EA56940"), r.nextInt(40000));
        list.add(p);
        p = new Player("Plakias", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/89571023_10157331161184755_8298678858064855040_o.jpg?_nc_cat=108&_nc_sid=85a577&_nc_oc=AQkGftv59RroswUISD8yzLVR1fHpxzQbiQA22JtlwfybNl4jcVqJNQUTi4oJRb6KHdMEuGbRzUhUsZSQc_IPeCi4&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=973dec1eedf93d38d78998a608bfc1c4&oe=5EA37EA0"), r.nextInt(40000));
        list.add(p);
        p = new Player("Petridis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/82282765_10221572265081799_4470532648388788224_n.jpg?_nc_cat=103&_nc_sid=85a577&_nc_oc=AQlqPexoH9N_X7p7S_8126pWhbxQ36oNft7kBTlQHupxbFFQj-ph0FSCfT1iOhkVfD8ya3qvQ8ISF2SYn2Mi3vUc&_nc_ht=scontent-dus1-1.xx&oh=19f0c189a6e89e15064a3b9393609da6&oe=5EA33C2E"), r.nextInt(40000));
        list.add(p);
        p = new Player("Ramis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/40353169_2203811656563053_1456352869530730496_n.jpg?_nc_cat=101&_nc_sid=85a577&_nc_oc=AQmTg-ZOJfZxtqp_5nW-04y48pTe_QKvjxnn-Mx3E95NTR8VD7rNqmEOZB_6D0S6cdfotNuWGXjQ4ePdFW08Mw2_&_nc_ht=scontent-dus1-1.xx&oh=04a868eda9ead497517d5bbc943fa2ca&oe=5EA57FF5"), r.nextInt(40000));
        list.add(p);
        p = new Player("George Kalikatzaros", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/30708408_10216480433827779_7797146773767585792_o.jpg?_nc_cat=108&_nc_sid=85a577&_nc_oc=AQn9r5f8pbprrZxDzGeBlwmnbXkiFNROb1YLEofpgsIxwoLtaUJ25m7PxHQkOd_-O3gcMmbd69gsx-WHrKuI8wxL&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=6c1996c1758a006fcc1b4889cf621c39&oe=5EA4DE5E"), r.nextInt(40000));
        list.add(p);
        p = new Player("Stefanidakis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/s960x960/88099172_10218710834675610_3551718880395132928_o.jpg?_nc_cat=100&_nc_sid=85a577&_nc_oc=AQnrl_tz_jsrMGrSEtycR-p1HXVtAyipmlmc5DgtxNTTPSDyGpCROdzoypJdb308oSbJE8ylTmNh_FSzEhtnfdl7&_nc_ht=scontent-dus1-1.xx&_nc_tp=7&oh=4180fd0b8da66bee500f754d4db843a1&oe=5EA27713"), r.nextInt(40000));
        list.add(p);
        p = new Player("Themelis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/p960x960/81179355_2202354536736967_7116701079361814528_o.jpg?_nc_cat=109&_nc_sid=85a577&_nc_oc=AQkfaoVhynmGGh-6UdlO6HPLJlapY2Xn04NlQt49oImeRlC0L6KdymUuVHIfLQd08kj-imCLtcPe4NOcrp3fdwDX&_nc_ht=scontent-dus1-1.xx&_nc_tp=6&oh=edfdc95376b0f68f4e260532d392edc2&oe=5EA1F458"), r.nextInt(40000));
        list.add(p);
        p = new Player("Glinatsis", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/16996133_1493981173968750_5315705743155037705_n.jpg?_nc_cat=106&_nc_sid=85a577&_nc_oc=AQlGnM_aie9NFZFOcXnFzaUtQP6T9_vQgq8qU63C8tsvsNEMwOTLFoEaeUs47QMGBWlXUaRdQFHZ5_pE0Mw9LhHC&_nc_ht=scontent-dus1-1.xx&oh=37428790e7917b79f997d397bee61709&oe=5EA41C74"), r.nextInt(40000));
        list.add(p);
        p = new Player("Spiros", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t1.0-9/87534644_209401733781064_8610731850144415744_n.jpg?_nc_cat=100&_nc_sid=85a577&_nc_oc=AQkzOpn70lIxStzxSp4bL16KHLw0BzVZ8UH44eQs9ZiZxsXCXc-HNFwhYzj9mlGiG_8_d1SiaEv1m16wFK-_T6pf&_nc_ht=scontent-dus1-1.xx&oh=fe192164342c738fbf6dcd3479ee8ce3&oe=5EA4F713"), r.nextInt(40000));
        list.add(p);
        p = new Player("Irini", "", Uri.parse("https://scontent-dus1-1.xx.fbcdn.net/v/t31.0-8/25182235_117365682386733_4795272968776989735_o.jpg?_nc_cat=102&_nc_sid=85a577&_nc_oc=AQkgIRhnJ0LNq8I1JRmrKYynXr2ayBj8Uw09kHIuZkwtNdf8zhAAzDpSzrwB6TpCXQjZ_IbwZvFX2Xt2FfLSOISE&_nc_ht=scontent-dus1-1.xx&oh=aa18b34bfcb5b9824c85ec764c7a4d99&oe=5EA2E486"), r.nextInt(40000));
        list.add(p);
        return list;
    }

    private void delay(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
        }
    }
}
