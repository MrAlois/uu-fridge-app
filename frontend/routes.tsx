import {createBrowserRouter, Navigate, RouteObject} from "react-router-dom";
import React from "react";
import FoodListingDetailView from "Frontend/views/listing/FoodListingDetailView";
import FoodListingCreateView from "Frontend/views/listing/FoodListingCreateView";
import UserDetailView from "Frontend/views/user/UserDetailView";
import MainLayout from "Frontend/views/MainLayout";
import LoginView from "Frontend/views/login/LoginView";
import FoodListingDashboardView from "Frontend/views/listing/FoodListingDashboardView";

export const routes: readonly RouteObject[] = [
    {
        element: <MainLayout />,
        children: [
            { path: "/", element: <Navigate to="/food-listings" /> },
            { path: "/food-listings", element: <FoodListingDashboardView /> },
            { path: "/food-listings/:listingId", element: <FoodListingDetailView /> },
            { path: "/add-listing", element: <FoodListingCreateView />},
            { path: "/profile", element: <UserDetailView />},
            { path: "/login", element: <LoginView />}
        ]
    }
];

export const router = createBrowserRouter([...routes], {basename: new URL(document.baseURI).pathname });