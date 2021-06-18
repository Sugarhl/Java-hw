package sugar.samples;

import sugar.anotations.AnyOf;
import sugar.anotations.Constrained;
import sugar.anotations.NotNull;
import sugar.anotations.Size;
import sugar.validation.ValidateManager;
import sugar.validation.ValidationError;
import sugar.validation.Validator;

import java.util.List;
import java.util.Set;

@Constrained
public class BookingForm {
    @NotNull
    @Size(min = 1, max = 5)
    private List<@NotNull GuestForm> guests;
    @NotNull
    private List<@AnyOf({"TV", "Kitchen"}) String> amenities;
    @NotNull
    @AnyOf({"House", "Hostel"})
    private String propertyType;
    @NotNull
    private Unrelated unrelated;

    public BookingForm(List<GuestForm> guests, List<String> amenities, String
            propertyType, Unrelated unrelated) {
        this.guests = guests;
        this.amenities = amenities;
        this.unrelated = unrelated;
        this.propertyType = propertyType;
    }
}

