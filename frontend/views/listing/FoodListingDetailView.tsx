import React, {useContext, useEffect, useState} from 'react';
import Gallery from '../../components/Gallery';
import {FoodListingEndpoint} from "Frontend/generated/endpoints";
import {NavLink, useNavigate, useParams} from "react-router-dom";
import {Tabs} from "@hilla/react-components/Tabs";
import {Tab} from "@hilla/react-components/Tab";
import {Icon} from "@hilla/react-components/Icon";
import {ConfirmDialog} from "@hilla/react-components/ConfirmDialog";
import StateBadge from "Frontend/components/StateBadge";
import ClaimState from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/ClaimState";
import FoodListing from "Frontend/generated/cz/asen/unicorn/fridge/domain/FoodListing";
import {Tooltip} from "@hilla/react-components/Tooltip";
import {UserContext} from "Frontend/components/UserProvider";
import User from "Frontend/generated/cz/asen/unicorn/fridge/domain/User";
import {Notification} from '@hilla/react-components/Notification.js';

const iconStyle= "h-[var(--lumo-icon-size-s)] m-auto w-[var(--lumo-icon-size-s)]"

export default function FoodListingDetailView() {
    const { listingId } = useParams()
    const navigate = useNavigate()
    const [removeDialogOpened, setRemoveDialogOpened] = useState(false);
    const [unclaimDialogOpened, setUnclaimDialogOpened] = useState(false);
    const [listing, setListing] = useState<FoodListing>()

    const { currentUser } = useContext(UserContext);
    const isCurrentUserOwner = currentUser?.email === listing?.donor?.email;
    const isCurrentUserClaiming = currentUser?.id === listing?.currentClaimingUser?.id;

    useEffect(() => {
        FoodListingEndpoint.getFoodListingById(listingId as unknown as number)
            .then((result) => setListing(result))
            .catch(e => Notification.show(`Couldn't find listing. ${JSON.stringify(e)}`, {
                position: 'top-stretch',
                theme: 'error',
                duration: 4000,
            }))
    }, []);

    const claimFoodListing = (listingId: number, user: User) => FoodListingEndpoint.claimListing(listingId, user)
        .then(result => Notification.show('Listing claimed', {
            position: 'top-center',
            theme: 'success',
            duration: 3000,
        }))
        .catch(e => Notification.show(`Couldn't claim listing. ${JSON.stringify(e)}`, {
            position: 'top-stretch',
            theme: 'error',
            duration: 4000,
        }))
        .finally(() => navigate("/food-listings"))

    const unclaimFoodListing = (listingId: number, user: User) => FoodListingEndpoint.unclaimListing(listingId, user)
        .then(result => Notification.show('Listing unclaimed', {
            position: 'top-center',
            theme: 'success',
            duration: 3000,
        }))
        .catch(e => Notification.show(`Couldn't unclaim listing. ${JSON.stringify(e)}`, {
            position: 'top-stretch',
            theme: 'error',
            duration: 4000,
        }))
        .finally(() => navigate("/food-listings"))

    return (
        <>
            <ConfirmDialog
                header='Remove listing'
                cancelButtonVisible
                confirmText="Delete"
                confirmTheme="error primary"
                opened={removeDialogOpened}
                onOpenedChanged={(event) => setRemoveDialogOpened(event.detail.value)}
                onConfirm={() =>
                    FoodListingEndpoint.deleteFoodListing(Number(listingId))
                        .then(result => Notification.show('Listing deleted', {
                            position: 'top-center',
                            theme: 'success',
                            duration: 3000,
                        }))
                        .catch(e => Notification.show(`Couldn't delete listing. ${JSON.stringify(e)}`, {
                            position: 'top-stretch',
                            theme: 'error',
                            duration: 4000,
                        }))
                        .finally(() => navigate("/food-listings"))
                }
            >
                <p>Are you sure you want to remove this listing?</p>
            </ConfirmDialog>

            <ConfirmDialog
                header='Unclaim listing'
                cancelButtonVisible
                confirmText="Unclaim"
                confirmTheme="error primary"
                opened={unclaimDialogOpened}
                onOpenedChanged={(event) => setUnclaimDialogOpened(event.detail.value)}
                onConfirm={() => unclaimFoodListing(1, currentUser)} //FIXME !!!
            >
                <p>If you're cancelling your claim, you should contact your donor. Are you sure?</p>
                <p className="my-4"><span>Email: </span> <a href={`mailto:${listing?.donor?.email}`}>{listing?.donor?.email}</a></p>
            </ConfirmDialog>

            <div className="p-4 mx-auto max-w-4xl">
                <div className="flex justify-between items-start border-b pb-3 mb-3">
                    <h1 className="text-2xl overflow-wrap">{listing?.shortDescription}</h1>
                    <div className="text-xs text-gray-500">{new Date(listing?.created as string).toDateString()}</div>
                </div>

                <Gallery images={listing?.base64Images}/>

                <section className="my-10">
                    <div className="text-2xm leading-none tracking-tight text-gray-900 md:text-3xl">
                        Listing Information
                    </div>
                    <div className="grid grid-cols-4 w-full h-full mt-5">
                        <div className="border-b py-2 col-span-1 font-bold">Listing state:</div>
                        <div className="border-b py-2 col-span-3 flex items-center">
                            <StateBadge state={listing?.currentState}/>
                            {isCurrentUserOwner && (
                                <span className="mx-4 bg-green-500 text-white rounded-full py-1 px-3 ml-4">This is your listing!</span>
                            )}
                        </div>

                        <div className="border-b py-2 col-span-1 font-bold">Donor Name:</div>
                        <div className="border-b py-2 col-span-3">{listing?.donor?.name}</div>

                        <div className="border-b py-2 col-span-1 font-bold">Donor Contacts:</div>
                        <div className="border-b py-2 col-span-3 flex flex-col">
                            <a href={`mailto:${listing?.donor?.email}`}>{listing?.donor?.email}</a>
                            <p>{listing?.donor?.phone}</p>
                        </div>

                        <div className="border-b py-2 col-span-1 font-bold">Pickup Location:</div>
                        <div className="border-b py-2 col-span-3">
                            <p>{listing?.pickupLocation}</p>
                        </div>

                        {listing?.allergens?.length !== 0 && (
                            <>
                                <div className="border-b py-2 col-span-1 font-bold">Dietary Properties:</div>
                                <div className="border-b py-2 col-span-3">{listing?.allergens?.join(", ")}</div>
                            </>
                        )}

                        <div className="border-b py-2 col-span-1 font-bold">Description:</div>
                        <div className="border-b py-2 col-span-3">
                            <p>{listing?.description}</p>
                        </div>
                    </div>
                </section>
                {listing?.currentClaimingUser && (
                    <section className="my-10">
                        <span className="text-2xl leading-none tracking-tight text-gray-900 md:text-5x">Claimant information</span>

                        <div className="mt-5 space-y-4">
                            <div className="grid grid-cols-4 py-2 border-b">
                                <strong className="col-span-1">Name:</strong>
                                <div className="col-span-3 w-full">{listing.currentClaimingUser.name}</div>
                            </div>

                            <div className="grid grid-cols-4 py-2 border-b">
                                <strong className="col-span-1">Contact:</strong>
                                <div className="col-span-3 w-full">
                                    <p><a href={`mailto:${listing.currentClaimingUser.email}`}>{listing.currentClaimingUser.email}</a></p>
                                    <p>{listing.currentClaimingUser.phone}</p>
                                </div>
                            </div>

                            <div className="grid grid-cols-4 py-2 border-b">
                                <strong className="col-span-1">Claimed At:</strong>
                                <div className="col-span-3 w-full">{new Date(listing.claimTime as string).toUTCString()}</div>
                            </div>
                        </div>
                    </section>
                )}
            </div>

            <Tabs slot="navbar touch-optimized" theme="minimal equal-width-tabs"
                  className="w-full bottom-0 lg:flex lg:justify-center lg:gap-6">
                <Tab aria-label="Back">
                    <NavLink to="/food-listings" tabIndex={-1}>
                    <Icon icon="vaadin:arrow-circle-left" className={iconStyle}/>
                    </NavLink>
                    <Tooltip slot="tooltip" text="Back" position="top" />
                </Tab>

                {listing?.currentState == ClaimState.UNCLAIMED && !isCurrentUserOwner && (
                    <Tab aria-label="Claim">
                        <NavLink to="#" onClick={() => claimFoodListing(listing.id!, currentUser)} tabIndex={-1}>
                            <Icon icon="vaadin:location-arrow-circle" className={iconStyle}/>
                        </NavLink>
                        <Tooltip slot="tooltip" text="Claim" position="top" />
                    </Tab>
                )}

                {isCurrentUserOwner && (
                    <Tab aria-label="Edit">
                        <NavLink to="#" onClick={() => alert("Editing listing")} tabIndex={-1}>
                            <Icon icon="vaadin:ellipsis-circle" className={iconStyle}/>
                        </NavLink>
                        <Tooltip slot="tooltip" text="Edit" position="top" />
                    </Tab>
                )}

                {isCurrentUserOwner && (
                    <Tab aria-label="Cancel">
                        <NavLink to="#" onClick={() => setRemoveDialogOpened(true)} tabIndex={-1}>
                            <Icon icon="vaadin:close-circle" className={iconStyle}/>
                        </NavLink>
                        <Tooltip slot="tooltip" text="Cancel" position="top" />
                    </Tab>
                )}

                {isCurrentUserClaiming && (
                    <Tab aria-label="Remove Claim">
                        <NavLink to="#" onClick={() => setUnclaimDialogOpened(true)} tabIndex={-1}>
                            <Icon icon="vaadin:close-circle" className={iconStyle}/>
                        </NavLink>
                        <Tooltip slot="tooltip" text="Remove Claim" position="top" />
                    </Tab>
                )}
            </Tabs>
        </>
    );
};