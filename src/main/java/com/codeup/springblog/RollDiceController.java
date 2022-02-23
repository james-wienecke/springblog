package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class RollDiceController {
    private int result;
    private static final Random random = new Random();

    @GetMapping("/roll-dice")
    public String rollDice() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{num}")
    public String rollDiceGuess(@PathVariable int num, Model model) {
        model.addAttribute("guess", num);
        result = random.nextInt(5) + 1;
        model.addAttribute("result", result);
        return "roll-dice";
    }
}
