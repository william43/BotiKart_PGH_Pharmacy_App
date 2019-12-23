package com.example.pgh_pharmacy_app.dialogs.shopping;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.pgh_pharmacy_app.HorizontalNumberPicker;
import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class PrescribedShoppingCartDialog extends BottomSheetDialogFragment {
    private PrescribedBottomSheetListener mListener;

    HorizontalNumberPicker number;
    Button addtoCart;
    Button proceedtoCheckout;
    MedicineModel item;

    TextView medicineDetails;

    Uri mImageURI;

    ProgressBar progressBar;

    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    StorageTask mUploadTask;

    TextView tv;
    ImageView receiptImage;

    public PrescribedShoppingCartDialog(MedicineModel item){
        this.item = item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_prescribed_shopping_cart_item, container, false);

        number = v.findViewById(R.id.prescribed_quantity_horizontal_number_picker);
        addtoCart = v.findViewById(R.id.prescribed_addtoCart);
        proceedtoCheckout = v.findViewById(R.id.prescribed_proceedtoCart);


        progressBar = v.findViewById(R.id.prescribed_progressBar);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        tv = v.findViewById(R.id.prescribed_tv);
        receiptImage = v.findViewById(R.id.prescribed_addreceipt);
        receiptImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(getActivity()).start();
            }
        });
        medicineDetails = v.findViewById(R.id.prescribed_medicine_details);

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    upload("cart");
                }

            }
        });

        proceedtoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload("checkout");
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        medicineDetails.setText(item.getGenericName());

    }

    public interface PrescribedBottomSheetListener {
        void onButtonPresClicked(String text, MedicineModel m, int quantity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (PrescribedBottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement PrescribedBottomSheetListener");
        }
    }

    public  void attachReceiptImage(Image image, Uri data){
         Glide.with(getActivity()).load(image.getPath()).into(receiptImage);
         mImageURI = data;
         tv.setVisibility(View.INVISIBLE);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void upload(String type){
        if (mImageURI != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageURI));

            mUploadTask = fileReference.putFile(mImageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                            PrescribedMedicineModel upload = new PrescribedMedicineModel(item.getGenericName(),item.getDosage(),item.getLowerTohighest(),item.getPrice(),"prescribed",taskSnapshot.getUploadSessionUri().toString());
                            mListener.onButtonPresClicked(type, upload, number.getValue());
                            dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    }) ;
        }
        else{
            Toast.makeText(getContext(), "No File was selected", Toast.LENGTH_LONG).show();
        }
    }


}
