import React, {useEffect, useState} from 'react';
import Gallery from './Gallery';
import FoodListing from "Frontend/generated/cz/asen/fridge/domain/FoodListing";
import {FoodListingEndpoint} from "Frontend/generated/endpoints";
import {useParams} from "react-router-dom";

export default function FoodListingDetailView() {
    const { listingId } = useParams()
    const [listing, setListing] = useState<FoodListing>()

    useEffect(() => {
        FoodListingEndpoint.getListing(listingId as unknown as number).then((result) => setListing(result))
    }, []);

    return (
        <div className="p-4">
            <div className="flex justify-between items-start border-b pb-3 mb-3">
                <h1 className="text-2xl overflow-wrap">{listing?.shortDescription}</h1>
                <div className="text-xs text-gray-500">{new Date(listing?.created as string).toDateString()}</div>
            </div>

            <Gallery images={listing?.base64Images} />

            <div className="py-2">
                <h2 className="font-bold mb-2">Donor Information:</h2>
                <p>Name: {listing?.donor?.name}</p>
                <p>Email: {listing?.donor?.email}</p>
                <p>Mobile: {listing?.donor?.phone}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Expires On:</h2>
                <p>{new Date(listing?.expiryDate as string).toDateString()}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Pickup Location:</h2>
                <p>{listing?.pickupLocation}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Dietary Properties:</h2>
                <p>{listing?.allergens?.join(", ")}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Description:</h2>
                <p>{listing?.description}</p>
            </div>
        </div>
    );
};