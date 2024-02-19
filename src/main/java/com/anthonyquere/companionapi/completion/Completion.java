package com.anthonyquere.companionapi.completion;

import com.anthonyquere.companionapi.crud.companions.Companion;

public interface Completion {
    CompletionOutput answerMessage(Companion companion, String question) throws Exception;
}
