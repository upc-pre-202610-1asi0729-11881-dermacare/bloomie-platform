package com.bloomie.platform.routinemanagement.domain.model.commands;

public record ReplaceProductInRoutineCommand(Long routineId,
                                             Long routineItemId,
                                             String newProductRecommendation) {
}
