import React, {createContext, useEffect, useMemo, useState} from 'react';
import {UserEndpoint} from "Frontend/generated/endpoints";
import User from "Frontend/generated/cz/asen/unicorn/fridge/domain/User";

interface UserContextType {
    currentUser: User,
    appUsers: User[],
    selectUserByEmail: (email: string) => void;
}

// Initial state
const initialState: UserContextType = {
    currentUser: {
        id: 0,
        name: "Default User",
        // define other fields as per your User structure.
    },
    appUsers: [],
    selectUserByEmail: () => {}
}

// Create context
export const UserContext = createContext<UserContextType>(initialState);

export const UserProvider = ({ children }: {children: React.ReactNode}) => {
    const [currentUser, setCurrentUser] = useState<User>(initialState.currentUser);
    const [appUsers, setAppUsers] = useState<User[]>([]);

    useEffect(() => {
        UserEndpoint.getAllUsers().then((result: (User | undefined)[]) => {
            const filteredResults: User[] = result.filter((user): user is User => !!user)
            setCurrentUser(filteredResults[0] || initialState.currentUser);
            setAppUsers(filteredResults);
        });
    }, []);

    const selectUserByEmail = (email: string) => {
        const foundUser = appUsers.find(user => user.email === email)
        if (foundUser) {
            console.log(`Set user to ${email}`)
            setCurrentUser(foundUser);
        }
    }

    // Wrapping the user state and selectUserByEmail inside a useMemo to prevent unnecessary re-renders
    const contextValue = useMemo(() => ({
        currentUser, appUsers, selectUserByEmail
    }), [currentUser, appUsers, selectUserByEmail]);

    return (
        <UserContext.Provider value={contextValue}>
            {children}
        </UserContext.Provider>
    );
};