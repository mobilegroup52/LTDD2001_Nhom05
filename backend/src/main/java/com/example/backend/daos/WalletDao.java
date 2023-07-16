package com.example.backend.daos;

import com.example.backend.dtos.WalletDto;
import com.example.backend.models.AccountType;
import com.example.backend.models.User;
import com.example.backend.models.Wallet;
import com.example.backend.repositories.AccountTypeRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class WalletDao {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    private ModelMapper modelMapper;

    public List<WalletDto> getAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();

        return wallets.stream().map((a) -> modelMapper.map(a, WalletDto.class))
                .collect(Collectors.toList());
    }

    public WalletDto getWallet(int id){
        Optional<Wallet> wallet = walletRepository.findById(id);
        return modelMapper.map(wallet, WalletDto.class);
    }

    public List<WalletDto> getWalletByUserId(int id) throws Exception {
        User user = userRepository.findById(id);
//                .orElseThrow(Exception::new);
        List<Wallet> wallets = walletRepository.findByUserId(user);

        return wallets.stream().map((a) -> modelMapper.map(a, WalletDto.class))
                .collect(Collectors.toList());
    }

    public WalletDto addNewWallet(WalletDto walletDto) throws Exception {
        User user = userRepository.findById(walletDto.getUserId())
                .orElseThrow(Exception::new);
        AccountType accountType = accountTypeRepository.findById(walletDto.getAccount_TypeId())
                .orElseThrow(Exception::new);

        Wallet wallet = new Wallet();
        wallet.setName(walletDto.getName());
        wallet.setDescription(walletDto.getDescription());
        wallet.setBalance(walletDto.getBalance());
        wallet.setUser((user));
        wallet.setAccountType(accountType);

        return modelMapper.map(walletRepository.save(wallet), WalletDto.class);
    }

    public WalletDto updateWallet(int id, WalletDto walletDto) throws Exception {
        User user = userRepository.findById(walletDto.getUserId())
                .orElseThrow(Exception::new);
        AccountType accountType = accountTypeRepository.findById(walletDto.getAccount_TypeId())
                .orElseThrow(Exception::new);
        Wallet wallet = walletRepository.findById(id)
                        .orElseThrow(Exception::new);

        wallet.setName(walletDto.getName());
        wallet.setDescription(walletDto.getDescription());
        wallet.setBalance(walletDto.getBalance());
        wallet.setUser((user));
        wallet.setAccountType(accountType);
        Wallet updatedWallet = walletRepository.save(wallet);

        return modelMapper.map(updatedWallet, WalletDto.class);
    }

    public WalletDto patchWallet(int id, WalletDto walletDto) throws Exception {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(Exception::new);

        if (walletDto.getName() != null) {
            wallet.setName(walletDto.getName());
        }

        if (walletDto.getBalance() != null) {
            wallet.setBalance(walletDto.getBalance());
        }

        if (walletDto.getDescription() != null) {
            wallet.setDescription(walletDto.getDescription());
        }

        if(walletDto.getUserId() != null) {
            User user = userRepository.findById(walletDto.getUserId())
                    .orElseThrow(Exception::new);
            wallet.setUser(user);
        }

        if(walletDto.getAccount_TypeId() != null) {
            AccountType accountType = accountTypeRepository.findById(walletDto.getAccount_TypeId())
                    .orElseThrow(Exception::new);
            wallet.setAccountType(accountType);
        }

        Wallet updatedWallet = walletRepository.save(wallet);

        return modelMapper.map(updatedWallet, WalletDto.class);
    }

    public boolean deleteWallet(int id) {
        if (walletRepository.existsById(id)) {
            walletRepository.deleteById(id);
            return true;
        } else return false;
    }
}
