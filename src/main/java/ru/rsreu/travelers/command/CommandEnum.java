package ru.rsreu.travelers.command;

import ru.rsreu.travelers.command.admin.*;
import ru.rsreu.travelers.command.driver.*;
import ru.rsreu.travelers.command.moderator.*;
import ru.rsreu.travelers.command.passenger.*;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
    OPEN_TRIPS {
        {
            this.command = new OpenTripsCommand();
        }
    },

    OPEN_MY_TRIPS {
        {
            this.command = new OpenMyTripsCommand();
        }
    },

    OPEN_DRIVER_TRIPS {
        {
            this.command = new OpenDriverTripsCommand();
        }
    },

    OPEN_TRIP {
        {
            this.command = new PassengerOpenTripCommand();
        }
    },

    OPEN_DRIVER_TRIP {
        {
            this.command = new DriverOpenTripCommand();
        }
    },

    SHOW_CREATING_TRIP {
        {
            this.command = new ShowCreatingTripCommand();
        }
    },

    SHOW_ACCOUNTS {
        {
            this.command = new ShowAccountsCommand();
        }
    },

    SHOW_AUTHORIZED_ACCOUNTS {
        {
            this.command = new ShowAuthorizedAccountsCommand();
        }
    },

    SHOW_CREATING_ACCOUNT {
        {
            this.command = new ShowCreatingAccountCommand();
        }
    },

    SHOW_EDITING_ACCOUNT {
        {
            this.command = new ShowEditingAccountCommand();
        }
    },

    OPEN_TRIPS_BY_MODERATOR {
        {
            this.command = new OpenTripsModeratorCommand();
        }
    },

    OPEN_TRIP_BY_MODERATOR {
        {
            this.command = new ModeratorOpenTripCommand();
        }
    },

    SHOW_ACCOUNTS_FOR_MODERATOR {
        {
            this.command = new ShowUsersModeratorCommand();
        }
    },
    SHOW_LOCALITIES {
        {
            this.command = new ShowLocalitiesCommand();
        }
    },
    SHOW_EDITING_LOCALITY {
        {
            this.command = new ShowEditingLocalityCommand();
        }
    },

    SHOW_CREATING_LOCALITY {
        {
            this.command = new ShowCreatingLocalityCommand();
        }
    },

    BOOK_TRIP {
        {
            this.command = new BookTripCommand();
        }
    },

    REFUSAL_TRIP {
        {
            this.command = new RefusalTripCommand();
        }
    },

    DRIVER_APPROVE {
        {
            this.command = new DriverApproveCommand();
        }
    },

    DRIVER_DISAPPROVE {
        {
            this.command = new DriverDisapproveCommand();
        }
    },

    CLOSE_TRIP {
        {
            this.command = new CloseTripCommand();
        }
    },

    CANCEL_TRIP {
        {
            this.command = new CancelTripCommand();
        }
    },

    CREATE_ACCOUNT {
        {
            this.command = new CreateAccountCommand();
        }
    },

    DELETE_ACCOUNT {
        {
            this.command = new DeleteAccountCommand();
        }
    },

    EDIT_ACCOUNT {
        {
            this.command = new EditAccountCommand();
        }
    },

    DELETE_TRIP {
        {
            this.command = new DeleteTripCommand();
        }
    },

    BLOCK_USER {
        {
            this.command = new BlockUserCommand();
        }
    },

    UNBLOCK_USER {
        {
            this.command = new UnblockUserCommand();
        }
    },

    EDIT_TRIP {
        {
            this.command = new EditTripCommand();
        }
    },

    CREATE_LOCALITY {
        {
            this.command = new CreateLocalityCommand();
        }
    },

    EDIT_LOCALITY {
        {
            this.command = new EditLocalityCommand();
        }
    },

    DELETE_LOCALITY {
        {
            this.command = new DeleteLocalityCommand();
        }
    },

    CREATE_TRIP {
        {
            this.command = new CreateTripCommand();
        }
    },

    RATE_DRIVER {
        {
            this.command = new RateDriverCommand();
        }
    },

    RATE_PASSENGER {
        {
            this.command = new RatePassengerCommand();
        }
    },
    ;

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}