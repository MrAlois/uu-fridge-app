import { AppLayout, type AppLayoutElement } from '@hilla/react-components/AppLayout.js';
import React, {useEffect, useRef} from 'react';
import {Outlet} from "react-router-dom";
import {MenuBar} from "@hilla/react-components/MenuBar";
import {createRoot} from "react-dom/client";
import {Icon} from "@hilla/react-components/Icon";

function menuComponent(component: React.ReactNode) {
    const container = document.createElement('vaadin-menu-bar-item');
    createRoot(container).render(component);
    return container;
}

function createMenuItem(iconName: string, text: string, isChild = false) {
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

    const menuItems = [
        {
            component: createMenuItem('menu', ''),
            children: [
                { component: createMenuItem('user', 'Account', true) },
                { component: createMenuItem('notebook', 'My Claims', true) },
                { component: 'hr' },
                { component: createMenuItem('sign-out', 'Logout', true) },
            ],
        }
    ];

    return (
        <AppLayout ref={appLayoutRef}>
            <header slot="navbar" className="bg-gray-800 p-4 text-white w-full">
                <div className="flex items-center justify-between">
                    <MenuBar items={menuItems} onItemSelected={(event) => alert(JSON.stringify(event.detail.value))}/> Food Rescue Fridge App
                </div>
            </header>

            <Outlet/>

            {/* TODO Ideally put action bar here if possible */}
        </AppLayout>
    );
};