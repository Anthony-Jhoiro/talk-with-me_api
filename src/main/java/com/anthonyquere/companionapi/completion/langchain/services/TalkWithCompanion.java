package com.anthonyquere.companionapi.completion.langchain.services;

import com.anthonyquere.companionapi.crud.companions.Companion;
import dev.langchain4j.service.*;

public interface TalkWithCompanion {

    @SystemMessage("From this context: {{context}}\nAnswer at best but keep it short and simple")
    String chat(@MemoryId Companion companion, @V("context") String context, @UserMessage String message, @UserName String username);
}
