package com.pharmasante.pharmasanteProyect;
import com.pharmasante.pharmasanteProyect.EntitiesDto.ProductoDTO;
import com.pharmasante.pharmasanteProyect.Excepciones.ProductException;
import com.pharmasante.pharmasanteProyect.models.Categoria;
import com.pharmasante.pharmasanteProyect.models.Proveedor;
import com.pharmasante.pharmasanteProyect.services.IStorageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestStoreImg {

    @Autowired
    IStorageService storageService;

    private String bytes="iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAaVBMVEX///9XV1b+/v5QUE9dXVzx8fH4+PimpqXq" +
            "6upra2pUVFNVVVT19fVaWlmysrKNjY11dXXb29vLy8q8vLxvb26FhYXj4+N+fn5KSklkZGOdnZ3W1tbOzs6Tk5O5ubnFxcWrq6tFRUSgoKBXmE" +
            "8FAAAIt0lEQVR4nO2dDbeiLBDHEdQKtDSt1DK79/t/yAe7L+dmMaIx4D6H/9mze3a3C/wCYRhGhhAvLy8vLy/zouhySncnjHHllDBZ511xXeGq" +
            "c4Moa43PXXMKOcNWvXcwUGWNUdGUAeMBvkR2dgCYFIeQCwt4vfjpaJmPknMW2sKTSoNdZBWQkNbK6Pwj1ticUemxZHb5pD6uxBpi3NWWO/ALc" +
            "W8L0RFgIEJLsw3tNk4ApbKjhV6kZO+mB6VSsVtbAMxDV4C9Gvw1I3I2RO/iW3Tz7WB/mXhEvKDiUXJ1y9dPqJgWKiXH0ukY7cU3mBNq3Ljmk2" +
            "KHCA/xUls0tpViLcVCjFrnY/RLWxw+Qs6ha7Rv1R0KH00axyvFr3iNs2Yca9dkv+Kn3PyTSOl2KV0Y9BPq2vxsE7tfC/+INYlhPmly63Xh3bVYVSZ" +
            "8iBVUI18ZJ9QZpKwKsnZ1LYquMKBuVUGVdWbHKSWnsUHKq3R3TpIkNndacQK+VVHnRhEpGVsMRVbcKzTGJwnjDPhaeWkSkZIzOGICUW7jux/VrHJ" +
            "oemMng0b42GMoDjnBMBZpB/mdRWtuy09JCxGyHcLydFd8S4F6037gmBE80fASb0MTQTs2ERqbUCkBnnlR4zky5a4bnG3qs7HvFtgaVoZXpgdRsg" +
            "c6USKaej4iNSFrE0z/FyUdNI3zzNATEqnnNLnyompkluOtCUAqCdVdiO2lhW2bIL0ZQIQIwwLbRyutfmi2EbWBFgCENs6fKd1DCz/fvB/IABG2pj" +
            "dqLxuwhRZ+nr1toQKE6dUMw4iiFnJk8sO7to2aUBg18AGtwdmG7d5shZqQWzm1vM82G6gXqzcRAcKdMdt3rAkdtENN2XsTKvAcmnaXAG24QbaNCC" +
            "/vTKhqwvRmK0CCkgQ+vHwrMgwg3FqLcpHbDNhX9M7KrCasL/biePqTE2i2EW9Ehi2FkBRQJ6Z8NTsyDCC0tBx+K/4Ex+lHMbc1akLUM+cXinYw4" +
            "tzIMIAQywWlakiegefQ6czZZjGEUuDCf7ex5mhJhPQK+6bnRYYtiZDQHXxGtJpjRy6KkEQZjHidYb4ti3A0cGnGKf+yCCntUnA/HE73/i2LkJDk" +
            "Bk+o5eQJdWmEI06NGZFhiyOETzPklr+ZGBm2OEJKLpDzLZgcGbY4Qllp8QESTowMWx6hrBXe8YvNpC3/Agml4B0/m7TlXyYheJrxFRmmrWUS0pF" +
            "XP9in/oHDMglJvIWDfCZEhi2UkCSfIGEfGaapiYRYzMNyKVnDjyLfJJqNWWofUnKGF/5Q13qbRkhXZYii63NV5AKuijiEtK2EEEH/6+u34M9fh" +
            "n++/MfXPykEa14ggv5FDEL5reKF9G+e/c+wbYNDCEaivaeweNG0I3B2ikNY4PXhS3OaXtQLP85zuEZ7yZS9PnCm6u8UhZCS/MNIIPtzZHuoCNN" +
            "LGtV3ijSXEnL9xFCnXL6TgwIRaz3UKtOg5LBRvM+DRIh26ZC6eeuNZULL8oRaRXhCp/KEWkV4QqfyhFpFeEKn8oRaRXhCp/KEWkV4QqeyT4h9" +
            "W9WwfOuEFjp16MG0Syjra3ao2g7abZmQkv0H41M15SdYVeaDKm0SUnLEv02KtRF9qNMuIfginRmJx060TYh4MvMjsXFISEhuYZQ+vjtifaYpquk" +
            "zzSRVWU7dPYe9zp8Zpk7Xtdv18P+/4hO0cwvVAYa3vLWK8IRO5Qm1ivCETuUJtYrwhE7lCbWK8IRO5Qm1ivCETuUJtYrwO+DHT2NrwOigD7stqo" +
            "avalv3Jq6z1581pnCVDKu02odRjX09veC3+LF5dr368OUjRsQfX560fm6BR/ajwXWUtgkR3wr6EWserr21fga8qnCzKAhWX9yeW8RFluK8nvet9h" +
            "wPmmc9FiNOUDW8YsdHm+gU6S1vt/KEWkV4QqfyhFpFeEKn8oRaRXhCp/KEWkV4QqfyhFpFeEKn8oRaRXhCp3LgiTKNMFKBA8Iox1QyzPNmnTB" +
            "pBModQ793DWV7tx7heOTO9PfF027QPEzC4d1UlOwtnMzsBqdrRzzCpwweVt4KGmRAQL1F6SlLZn9Cio4ohiekiIThU94TSkYu9n9fgl0HVZ4R" +
            "Ca/PRUSHIEVVvR3OpRc8wuD26vPHPabyYdIjSq6qDnifkLcvP46spwcDk/DwKouU5agvQlaKEWaAUFjKDjjSPlWOJBPPYY2Z7lhP0qRRXc+" +
            "uTQjlA56YhABBQBKoUD8TmzKnM0dPB6wh1QWmotZuHCXKHJnTchCgKFElfRb1hFKUV4OL5zuobUuZF5Fn2mVQdaoFPiUFAY46lSHMT9oTDZ" +
            "j9e26+OlOKWtW3z9oJhFslIT/YyOoMSJ1yhunnEJXWuzqfG3e7JCbqXCWV4mrl1wJ27syhXUPJCriwfFJBQFYXXjoE7IDBpT/RwA9i/0Tr" +
            "5pEwK0oolP5hwmN4t4ygnXtv2VhHlHuMpIByzLFpaWDjErqUJd3l+FGXf3Wv6/gJJQgW5aRJnlJomPbmw921YAfyq5bjFn6xg90mtuao8" +
            "IX8fGPp6ZZ8129h9xtfmjKF48nriTkCadKM+NB4UDeXtYVOXJ+3uzoMRq4yGgT06+g86swWrKqCMrtfqnY67LLd4dT/lv3+Zfin/OjU/" +
            "8zC8KNifPympnD6pkdt/z12pcC9Q0nzFqpZG9cL/h1XxiTqGflWSdy4bvcENTPSAtPRNLXLUX+j24w5j5Kr65Zra07a47vgxJGLETvMw5O" +
            "KNv/COOWb2Q5AaYCHy0fk6ayH8AdxJHHkAsTf9MPTbuEDlW+6Ny3HuFt0L8oenLES/kOIJgDlQD2WS100WHk0sbmRZbRsid3IWWvoBFraC" +
            "+cMciA4kQizszknQ+8EOoS6OxkLEjw8FEY9frKoqGjKYBGjlbOgbIrIdIyELC0+d80p5KiRiOPi4anp+hfYcRwoyTrviuvKna5Flx8xz6H" +
            "7743GLkUJfgQPrvtQ17/o5eXl5eVlUv8BEv74srj+WGIAAAAASUVORK5CYII=";
    ProductoDTO productoDTO;

    @BeforeEach
    void inicializarValores(){
        productoDTO = new ProductoDTO("Loratadina", new Categoria(1, "Cosmeticos"),
                new Proveedor(1, "La sante", "555", "correo1"),
                "imagen.png", 500, 700, bytes);
    }

    @Test
    void testStoreImage(){
        String ruta = storageService.store(productoDTO);

        Assertions.assertThat(ruta).isEqualTo("imgTest/imagen.png");
    }
    @Test
    void testStoreImageException(){
        productoDTO.setBytesImg("sin formato");
        assertThrows(ProductException.class,()->{
            storageService.store(productoDTO);;
        });

        productoDTO = null;
        assertThrows(ProductException.class,()->{
            storageService.store(productoDTO);;
        });
    }
}
