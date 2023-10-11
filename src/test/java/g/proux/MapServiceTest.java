package g.proux;

import g.proux.controller.service.MapService;
import g.proux.model.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MapServiceTest {

    private MapService mapService;

    @BeforeEach
    void setUp() {
        this.mapService = new MapService();
    }

    @Test
    @SneakyThrows
    public void testNominalInitMap() {
        Map map = new Map();
        List<String> lineElements = new ArrayList<>();
        lineElements.add("C");
        lineElements.add("3");
        lineElements.add("4");

        this.mapService.initMap(map, lineElements);

        assertThat(map.getWidth()).isEqualTo(3);
        assertThat(map.getHeight()).isEqualTo(4);
    }

}
