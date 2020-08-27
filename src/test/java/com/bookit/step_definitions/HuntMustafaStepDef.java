package com.bookit.step_definitions;

import com.bookit.pages.HuntPage;
import com.bookit.pages.MapPage;
import com.bookit.utilities.BrowserUtils;
import io.cucumber.java.en.When;

public class HuntMustafaStepDef {

    @When("user navigates to {string} page")
    public void user_navigates_to_page(String page) {
        BrowserUtils.waitForClickablility(new MapPage().hunt,10);
        new MapPage().navigateToPage(page);
        BrowserUtils.waitFor(2);
    }

    @When("user selects next day on calendar")
    public void user_selects_next_day_on_calendar() {
        new HuntPage().selectTomorrow();
    }

}
