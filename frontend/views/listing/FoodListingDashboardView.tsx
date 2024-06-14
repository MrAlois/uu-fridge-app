import React, {useContext, useEffect, useState} from "react";
import Gallery from "Frontend/components/Gallery";
import {NavLink} from "react-router-dom";
import {FoodListingEndpoint} from "Frontend/generated/endpoints";
import {Tab} from "@hilla/react-components/Tab";
import {Icon} from "@hilla/react-components/Icon";
import {Tabs} from "@hilla/react-components/Tabs";
import {TextField} from "@hilla/react-components/TextField";
import {Select} from "@hilla/react-components/Select";
import {truncateText} from "Frontend/util/text-utils";
import StateBadge from "Frontend/components/StateBadge";
import DistanceType from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/DistanceType";
import FoodListingSummary from "Frontend/generated/cz/asen/unicorn/fridge/endpoint/view/FoodListingSummary";
import {Tooltip} from "@hilla/react-components/Tooltip";
import {UserContext} from "Frontend/components/UserProvider";
import {Notification} from "@hilla/react-components/Notification";

const iconStyle: string = "h-[var(--lumo-icon-size-s)] m-auto w-[var(--lumo-icon-size-s)]"

export default function FoodListingDashboardView() {
    const [serverListings, setServerListings] = useState<FoodListingSummary[]>([])

    const { currentUser } = useContext(UserContext);
    const [namePatternFilter, setNamePatternFilter] = useState<string | undefined>(undefined)
    const [kmFilter, setKmFilter] = useState(DistanceType.ALL);

    useEffect(() => {
        FoodListingEndpoint.searchFoodByParams({owner: currentUser, namePattern: namePatternFilter, distanceType: kmFilter})
            .then((result) => {
                let listings = result == undefined ? [] as FoodListingSummary[] : result?.filter(it => it != null)
                setServerListings(listings as FoodListingSummary[])
            })
            .catch(e => Notification.show(`Couldn't finds listings. ${JSON.stringify(e)}`, {
                position: 'top-stretch',
                theme: 'error',
                duration: 4000,
            }));
    }, [namePatternFilter, currentUser]);

    const areaFilterItems = [
        { label: 'All', value: 'ALL' },
        { label: '< 2km', value: 'KM_2' },
        { label: '< 5km', value: 'KM_5' },
        { label: '< 10km', value: 'KM_10' },
        { label: '< 20km', value: 'KM_20' }
    ]

    return (
        <>
            <div className="mx-auto px-4 sm:px-0 w-full lg:max-w-4xl ">
                {/* Search bar section */}
                <div className="w-full flex flex-col sm:flex-row justify-between items-start sm:items-center border-gray-300 rounded-md shadow-sm mt-6 mb-6">
                    <TextField
                        className="w-full sm:w-2/3 p-2 my-2 sm:my-0"
                        label="Search"
                        placeholder="Search Listings..."
                        clearButtonVisible
                        onChange={e => setNamePatternFilter(e.target.value)}
                    >
                        <Icon slot="prefix" icon="vaadin:search" className="mr-2" />
                    </TextField>
                    <Select
                        label="Distance filter"
                        className="w-full sm:w-1/3 p-2 my-2 sm:my-0"
                        value={kmFilter.toString()}
                        items={areaFilterItems}
                        onChange={e => setKmFilter(DistanceType[e.target.value as keyof typeof DistanceType])}
                    />
                </div>

                {/* Listing content section */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mx-auto">
                    {serverListings.map((listing: FoodListingSummary) => (
                        <div key={listing.id}
                             className="flex flex-col border overflow-hidden rounded-md items-stretch w-full max-w-xs p-4 mx-auto">
                            <div className="flex justify-between mb-4">
                                <NavLink to={`/food-listings/${listing.id}`}>
                                    <h4 className="font-semibold">{truncateText(listing.shortDescription, 20)}</h4>
                                </NavLink>
                                <p className="text-sm text-gray-500">{new Date(listing?.created).toDateString()}</p>
                            </div>

                            <Gallery images={listing?.base64Images}/>

                            <div className="text-sm">
                                <p className="mb-2 mt-4"><strong>Donor:</strong> {listing?.donorName} {listing?.donorId === currentUser.id ? <span {...{ theme: 'badge success' }}>You</span> : ""}</p>
                                <p className="mb-2"><strong>Expires on:</strong> {new Date(listing?.expiryDate).toDateString()}</p>
                                <p className="mb-2"><strong>Address:</strong> {listing.pickupLocation}</p>
                                <p className="mb-2"><strong>State:</strong> <StateBadge state={listing.currentState}/></p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            <Tabs slot="navbar touch-optimized" theme="minimal equal-width-tabs" className="w-full bottom-0 lg:flex lg:justify-center lg:gap-6">
                <Tab aria-label="Create">
                    <NavLink to="/add-listing" tabIndex={-1}>
                        <Icon icon="vaadin:plus-circle" className={iconStyle}/>
                    </NavLink>
                    <Tooltip slot="tooltip" text="Create listing" position="top" />
                </Tab>
            </Tabs>
        </>
    );
}