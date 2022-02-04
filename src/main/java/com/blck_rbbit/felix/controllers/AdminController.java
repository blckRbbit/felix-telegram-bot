package com.blck_rbbit.felix.controllers;

import com.blck_rbbit.felix.entities.UserIdea;
import com.blck_rbbit.felix.entities.UserProfile;
import com.blck_rbbit.felix.services.AdminService;
import com.blck_rbbit.felix.services.UserIdeaService;
import com.blck_rbbit.felix.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserIdeaService userIdeaService;
    private final UserProfileService userProfileService;
    private final AdminService adminService;
    
    @GetMapping("/users")
    public String getAllUsers(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam(name = "last_name", required = false) Long lastName,
                              @RequestParam(name = "institution", required = false) String institution,
                              @RequestParam(name = "user_id", required = false) String userId) {
        Page<UserProfile> usersPage = userProfileService.find(page.orElse(1), lastName, institution, userId);
        model.addAttribute("usersPage", usersPage);
        int totalPages = usersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> usersPageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("usersPageNumbers", usersPageNumbers);
        }
        return "users";
    }
    
    @PostMapping("/users")
    public Page<UserProfile> findUsers(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam(name = "user_id", required = false) Long profileId,
                              @RequestParam(name = "last_name", required = false) String lastName,
                              @RequestParam(name = "institution", required = false) String institution
                              ) {
        Page<UserProfile> usersPage = userProfileService.find(page.orElse(1), profileId, lastName, institution);
        model.addAttribute("usersPage", usersPage);
        int totalPages = usersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return usersPage;
    }
    
    @GetMapping("/admin")
    public String getAdmin(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam(name = "estimated_costs", required = false) Integer cost,
                           @RequestParam(name = "idea_id", required = false) Long ideaId,
                           @RequestParam(name = "user_id", required = false) Long userId,
                           @RequestParam(name = "kind_of_idea", required = false) String ideaType
                           )
    {
        Page<UserIdea> ideasPage = userIdeaService.find(cost, page.orElse(1), ideaId, userId, ideaType);
        model.addAttribute("ideasPage", ideasPage);
        int totalPages = ideasPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin";
    }
    
    @PostMapping("/admin")
    public Page<UserIdea> search(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam(name = "estimated_costs", required = false) Integer cost,
                                 @RequestParam(name = "idea_id", required = false) Long ideaId,
                                 @RequestParam(name = "user_id", required = false) Long userId,
                                 @RequestParam(name = "kind_of_idea", required = false) String ideaType) {
        Page<UserIdea> ideasPage = userIdeaService.find(cost, page.orElse(1), ideaId, userId, ideaType);
        model.addAttribute("ideasPage", ideasPage);
        int totalPages = ideasPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return ideasPage;
    }
    
    @GetMapping("/admin/delete")
    public String getPageForDeleteAdmin() {
        return "delete_admin";
    }
    
    @PostMapping("/admin/delete")
    public String deleteAdminByTelegramName(@RequestParam(name = "login", required = false) String login) {
        adminService.deleteAdmin(login);
        return "success_remove_create";
    }
    
    @GetMapping("/admin/create")
    public String getPageForCreateAdmin() {
        return "create_admin";
    }
    
    @PostMapping("/admin/create")
    public String createAdmin(@RequestParam(name = "login", required = false) String login) {
        String result = "failure_create_admin";
        if (adminService.adminIsCreated(login)) {
            result = "success_remove_create";
        }
        return result;
    }
    
    @GetMapping("/idea/{id}/remove")
    public String refreshPageAfterIdeaRemove() {
        return "redirect:/admin";
    }
    
    @PostMapping("/idea/{id}/remove")
    public String removeIdea(@PathVariable(value = "id") Long id) {
        userIdeaService.removeIdea(id);
        return "redirect:/admin";
    }
}
