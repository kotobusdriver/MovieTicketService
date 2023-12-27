package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Platform {
    IMDB(10130),
    Letterboxd(10131),
    FilmIzle(10132),
    Filmora(10133),
    BollywoodMDB(10134);

    private final int id;

    public static Platform fromId(int platformId) {
        for (Platform p : Platform.values()) {
            if (p.id == platformId) {
                return p;
            }
        }
        throw new PlatformNotFoundException(platformId);
    }

    public static class PlatformNotFoundException extends RuntimeException {

        public PlatformNotFoundException(int platformId) {
            super("Requested platform with id:%s does not exist.".formatted(platformId));
        }
    }
}
