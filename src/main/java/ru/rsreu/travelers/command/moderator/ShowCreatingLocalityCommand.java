package ru.rsreu.travelers.command.moderator;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;
import jakarta.servlet.http.HttpServletRequest;
import ru.rsreu.travelers.command.ActionCommand;
import ru.rsreu.travelers.controller.Page;
import ru.rsreu.travelers.controller.TypeRedirect;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.TypeLocality;
import ru.rsreu.travelers.datalayer.data.UserGroup;

import java.util.ArrayList;
import java.util.List;

public class ShowCreatingLocalityCommand implements ActionCommand {
    private static Resourcer resourcer = ProjectResourcer.getInstance();

    @Override
    public Page execute(HttpServletRequest request) {
        String pagePath = resourcer.getString("path.page.creatingLocality");
        Page page = new Page(pagePath, TypeRedirect.FORWARD);
        List<TypeLocality> typeLocalities = new ArrayList<>();
        typeLocalities.add(new TypeLocality(1, "Город"));
        typeLocalities.add(new TypeLocality(2, "Посёлок"));
        typeLocalities.add(new TypeLocality(3, "Село"));
        typeLocalities.add(new TypeLocality(4, "Деревня"));
        request.setAttribute("currentPage", "creating_locality");
        request.setAttribute("typeLocalities", typeLocalities);
        return page;
    }

    @Override
    public int getGroupId() {
        return 2;
    }
}
