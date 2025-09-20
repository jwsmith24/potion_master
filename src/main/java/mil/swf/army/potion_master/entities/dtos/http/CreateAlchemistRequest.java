package mil.swf.army.potion_master.entities.dtos.http;

public record CreateAlchemistRequest(
        String name,
        Double gold
) {
}
