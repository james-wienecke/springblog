package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class RollDiceController {
    private static final Random random = new Random();

    @GetMapping("/roll-dice")
    public String rollDice() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{num}")
    public String rollDiceGuess(@PathVariable int num, Model model) {
        model.addAttribute("guess", num);
        int correct = 0;
        int[] result = new int[5];
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt(5) + 1;
            if (result[i] == num) correct++;
        }
        model.addAttribute("result", result);
        model.addAttribute("correct", correct);
        return "roll-dice";
    }
}
