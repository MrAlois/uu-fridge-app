import {AppLayout, type AppLayoutElement} from '@hilla/react-components/AppLayout.js';
import React, {useContext, useEffect, useRef, useState} from 'react';
import {Outlet} from "react-router-dom";
import {MenuBar} from "@hilla/react-components/MenuBar";
import {createRoot} from "react-dom/client";
import {Icon} from "@hilla/react-components/Icon";
import {MenuBarItem} from "@vaadin/menu-bar/src/vaadin-menu-bar-mixin";
import {UserContext} from "Frontend/components/UserProvider";

function menuComponent(component: React.ReactNode) {
    const container = document.createElement('vaadin-menu-bar-item');
    createRoot(container).render(component);
    return container;
}

function createMenuItem(iconName: string, text: (string | undefined), isChild = false) {
    const iconStyle: React.CSSProperties = {};
    if (isChild) {
        iconStyle.width = 'var(--lumo-icon-size-s)';
        iconStyle.height = 'var(--lumo-icon-size-s)';
        iconStyle.marginRight = 'var(--lumo-space-s)';
    }

    let ariaLabel = '';
    if (iconName === 'copy') {
        ariaLabel = 'duplicate';
    }

    return menuComponent(
        <>
            <Icon icon={`vaadin:${iconName}`} style={iconStyle} aria-label={ariaLabel} />
            {text}
        </>
    );
}

export default function MainLayout() {
    const appLayoutRef = useRef<AppLayoutElement>(null);
    useEffect(() => {
        const appLayout = appLayoutRef.current;
        if (appLayout) {
            // --vaadin-app-layout-touch-optimized is only enforced as part of this example
            appLayout.style.setProperty('--vaadin-app-layout-touch-optimized', 'true');
            (appLayout as any)._updateTouchOptimizedMode();
        }
    }, []);

    // DEV code to simulate authenticaiton
    const { currentUser, appUsers, selectUserByEmail } = useContext(UserContext)
    const [menuItems, setMenuItems] = useState<MenuBarItem[]>([]);
    useEffect(() => {
        const parsedUsers: MenuBarItem[] = appUsers.map(user => ({ text: user.email }));

        const newMenuItems = [
            {
                component: createMenuItem('menu', ''),
                children: [
                    { component: createMenuItem('user', currentUser.name, true) },
                    { component: 'hr' },
                    { component: createMenuItem('sign-out', 'Logout', true) },
                    {
                        component: createMenuItem('cheat', '[DEV] Select account', true),
                        children: parsedUsers
                    },
                ],
            }
        ];
        setMenuItems(newMenuItems);
    }, [appUsers, currentUser]);

    return (
        <AppLayout ref={appLayoutRef}>
            <header slot="navbar" className="bg-gray-800 p-4 text-white w-full">
                <div className="flex items-center justify-between">
                    <MenuBar items={menuItems} onItemSelected={
                        (event) => {
                            selectUserByEmail(event.detail.value.text ?? "");
                        }
                    }/>
                    Food Rescue Fridge App
                </div>
            </header>

            <Outlet/>
        </AppLayout>
    );
};